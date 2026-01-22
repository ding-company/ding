package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import org.springframework.stereotype.Component

@Component
class OtpFailureHandler(
    private val otpRepository: RedisOtpRepository,
    private val blockRepository: RedisOtpBlockRepository,
    private val publisher: AuthEventPublisher
) {
    fun handle(otp: OtpSession, contact: String) {
        val failed = otp.incrementTry()

        otpRepository.saveOtp(
            contact,
            failed,
            OtpSession.getRetryTrackTtl()
        )

        if (failed.isLockable()) {
            blockRepository.block(contact, OtpSession.getBlockDuration())
            publisher.publish(OtpAbuseDetectedEvent(contact))
            throw TooManyOtpAttemptsException()
        }
    }
}
