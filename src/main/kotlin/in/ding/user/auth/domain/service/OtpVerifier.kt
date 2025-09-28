package `in`.ding.user.auth.domain.service

import `in`.ding.common.exception.BaseHttpException
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.exception.OtpBlacklistedException
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import org.springframework.stereotype.Component

@Component
class OtpVerifier(
    private val otpRepository: RedisOtpRepository,
    private val blockRepository: RedisOtpBlockRepository,
    private val blacklistRepository: RedisOtpBlockRepository,
    private val publisher: AuthEventPublisher
) {

    fun checkAvailability(contact: String) {
        if (blockRepository.isBlocked(contact)) {
            publisher.publish(OtpAbuseDetectedEvent(contact))
            throw TooManyOtpAttemptsException()
        }
        if (blacklistRepository.isBlocked(contact)) {
            throw OtpBlacklistedException()
        }
    }
    fun verifyOtp(command: OtpVerifyCommand): Boolean {
        val otp = otpRepository.findOtp(command.contact) ?: throw OtpNotFound()

        try {
            val verifiedOtp = otp.verify(command.otpCode)
            otpRepository.saveOtp(command.contact, verifiedOtp, OtpSession.getOtpTtl())

            return true
        } catch (e: BaseHttpException) {
            when (e) {
                is InvalidOtpException, is ExpiredOtpException -> {
                    val updatedOtp = otp.incrementTry()
                    otpRepository.saveOtp(command.contact, updatedOtp, OtpSession.getRetryTrackTtl())

                    if (updatedOtp.isLockable()) {
                        blockRepository.block(command.contact, OtpSession.getBlockDuration())
                        publisher.publish(OtpAbuseDetectedEvent(command.contact))
                        throw TooManyOtpAttemptsException()
                    }
                }
            }
            return false
        }
    }
}
