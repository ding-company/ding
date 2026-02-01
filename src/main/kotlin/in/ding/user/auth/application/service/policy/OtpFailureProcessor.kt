package `in`.ding.user.auth.application.service.policy

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.user.auth.application.expiry.ExpiryResolver
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import org.springframework.stereotype.Component

@Component
class OtpFailureProcessor(
    private val otpRepository: RedisOtpRepository,
    private val blockRepository: RedisOtpBlockRepository,
    private val publisher: EventPublisher,
    private val expiryResolver: ExpiryResolver
) {
    fun handle(otp: OtpSession, contact: String) {
        val failed = otp.incrementTry()
        val otpLifeTime = DomainLifetime.OTP_SESSION
        val otpTtl = expiryResolver.resolve(otpLifeTime)

        otpRepository.saveOtp(
            contact,
            failed,
            otpTtl
        )

        if (failed.isLockable()) {
            val blockTtl = expiryResolver.resolve(SecurityLifetime.OTP_BLOCK)
            blockRepository.block(contact, blockTtl)
            publisher.publish(OtpAbuseDetectedEvent(contact))
            throw TooManyOtpAttemptsException()
        }
    }
}
