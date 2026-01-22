package `in`.ding.user.auth.application.service

import `in`.ding.common.exception.BaseHttpException
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.OtpBlacklistedException
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.enumerate.OtpAvailability
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class OtpVerifyServiceImpl(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val otpRepository: RedisOtpRepository,
    private val verifiedIdentityService: VerifiedIdentityService,
    private val tokenIssuer: TokenIssuer,
    private val publisher: AuthEventPublisher,
    private val otpFailureHandler: OtpFailureHandler
) : OtpVerifyService {

    @Transactional
    override fun verify(command: OtpVerifyCommand): OtpVerifyResponse {
        when (availabilityGuard.check(command.contact)) {
            OtpAvailability.BLOCKED -> {
                publisher.publish(OtpAbuseDetectedEvent(command.contact))
                throw TooManyOtpAttemptsException()
            }
            OtpAvailability.BLACKLISTED ->
                throw OtpBlacklistedException()
            OtpAvailability.OK -> Unit
        }
        availabilityGuard.check(command.contact)

        val otp = otpRepository.findOtp(command.contact)
            ?: throw OtpNotFound()

        val updatedOtp = try {
            otp.verify(command.otpCode, command.nationality)
        } catch (e: BaseHttpException) {
            otpFailureHandler.handle(otp, command.contact)
            throw e
        }

        otpRepository.saveOtp(
            command.contact,
            updatedOtp,
            OtpSession.getOtpTtl()
        )

        val verifiedIdentity = verifiedIdentityService.create(command)

        updatedOtp.drainEvents().forEach(publisher::publish)

        return OtpVerifyResponse.of(
            tokenIssuer.issueTokens(verifiedIdentity.exKey, UserStatus.TEMPORARY)
        )
    }
    override fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse.of(tokenIssuer.issueTokens(userExKey, UserStatus.REGISTERED))
    }
}
