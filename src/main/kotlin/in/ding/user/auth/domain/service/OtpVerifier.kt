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
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.exception.NotFoundUserException
import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class OtpVerifier(
    private val otpRepository: RedisOtpRepository,
    private val userRedisRepository: UserRedisRepository,
    private val blockRepository: RedisOtpBlockRepository,
    private val blacklistRepository: RedisOtpBlockRepository,
    private val userRepository: UserRepository,
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

    @Suppress("ThrowsCount")
    fun verifyOtp(command: OtpVerifyCommand): User {
        val otp = otpRepository.findOtp(command.contact) ?: throw OtpNotFound()
        val user = userRedisRepository.findTemporaryUser(command.contact) ?: throw NotFoundUserException()

        try {
            val verifiedOtp = otp.verify(command.otpCode)
            otpRepository.saveOtp(command.contact, verifiedOtp, OtpSession.getOtpTtl())
            return userRepository.save(user)
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
            throw e
        }
    }
}
