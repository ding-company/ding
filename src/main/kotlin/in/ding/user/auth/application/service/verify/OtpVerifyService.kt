package `in`.ding.user.auth.application.service.verify

import `in`.ding.common.exception.BaseHttpException
import `in`.ding.user.auth.application.expiry.ExpiryResolver
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.service.policy.OtpAvailabilityGuard
import `in`.ding.user.auth.application.service.policy.OtpFailureProcessor
import `in`.ding.user.auth.application.service.support.VerifiedIdentityAppService
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class OtpVerifyService(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val otpRepository: RedisOtpRepository,
    private val verifiedIdentityFactory: VerifiedIdentityAppService,
    private val tokenIssuer: TokenIssuer,
    private val publisher: AuthEventPublisher,
    private val otpFailureProcessor: OtpFailureProcessor,
    private val expiryResolver: ExpiryResolver
) {

    @Transactional
    fun verify(command: OtpVerifyCommand): OtpVerifyResponse {
        availabilityGuard.check(command.contact)
        val otp = otpRepository.findOtp(command.contact)
            ?: throw OtpNotFound()

        val updatedOtp = try {
            otp.verify(command.otpCode, command.nationality)
        } catch (e: BaseHttpException) {
            otpFailureProcessor.handle(otp, command.contact)
            throw e
        }
        val lifeTime = DomainLifetime.OTP_SESSION
        val ttl = expiryResolver.resolve(lifeTime)

        otpRepository.saveOtp(
            command.contact,
            updatedOtp,
            ttl
        )

        val verifiedIdentity = verifiedIdentityFactory.create(command.contact, command.nationality)
        updatedOtp.drainEvents().forEach(publisher::publish)

        return OtpVerifyResponse.of(
            tokenIssuer.issueTokens(verifiedIdentity.exKey, UserStatus.TEMPORARY)
        )
    }
    fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse.Companion.of(tokenIssuer.issueTokens(userExKey, UserStatus.REGISTERED))
    }
}
