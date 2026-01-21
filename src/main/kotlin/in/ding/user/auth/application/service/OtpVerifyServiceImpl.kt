package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.exception.OtpBlacklistedException
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.enumerate.OtpAvailability
import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.exception.NotFoundUserException
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OtpVerifyServiceImpl(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val otpRepository: RedisOtpRepository,
    private val blockRepository: RedisOtpBlockRepository,
    private val userRedisRepository: UserRedisRepository,
    private val tokenIssuer: TokenIssuer,
    private val publisher: AuthEventPublisher,
) : OtpVerifyService {

    @Transactional
    override fun verify(command: OtpVerifyCommand): OtpVerifyResponse {
        // 1. Availability 체크
        when (availabilityGuard.check(command.contact)) {
            OtpAvailability.BLOCKED -> {
                publisher.publish(OtpAbuseDetectedEvent(command.contact))
                throw TooManyOtpAttemptsException()
            }
            OtpAvailability.BLACKLISTED ->
                throw OtpBlacklistedException()
            OtpAvailability.OK -> Unit
        }

        // 2. 임시 유저 확인
        val tempUser = userRedisRepository
            .findTemporaryUser(command.contact)
            ?: throw NotFoundUserException()

        // 3. OTP 조회
        val otp = otpRepository.findOtp(command.contact)
            ?: throw OtpNotFound()

        // 4. 검증
        val updatedOtp = try {
            otp.verify(command.otpCode, command.nationality)
        } catch (e: InvalidOtpException) {
            handleFailure(otp, command.contact)
            throw e
        } catch (e: ExpiredOtpException) {
            handleFailure(otp, command.contact)
            throw e
        }

        // 5. 성공 저장
        otpRepository.saveOtp(
            contact = command.contact,
            otp = updatedOtp,
            ttl = OtpSession.getOtpTtl()
        )

        // 6. Domain Event publish
        updatedOtp.drainEvents().forEach(publisher::publish)

        // 7. Token 발급
        return OtpVerifyResponse.of(
            tokenIssuer.issueTokens(tempUser.exKey, UserStatus.TEMPORARY)
        )
    }

    private fun handleFailure(otp: OtpSession, contact: String) {
        val failed = otp.incrementTry()

        otpRepository.saveOtp(
            contact = contact,
            otp = failed,
            ttl = OtpSession.getRetryTrackTtl()
        )

        if (failed.isLockable()) {
            blockRepository.block(contact, OtpSession.getBlockDuration())
            publisher.publish(OtpAbuseDetectedEvent(contact))
            throw TooManyOtpAttemptsException()
        }
    }
    override fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse.of(tokenIssuer.issueTokens(userExKey, UserStatus.REGISTERED))
    }
}
