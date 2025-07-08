package `in`.ding.user.auth.domain.service

import `in`.ding.user.auth.domain.RedisOtpBlockRepository
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import java.time.Duration

class OtpBlockChecker(
    private val blockRepository: RedisOtpBlockRepository,
    private val publisher: AuthEventPublisher
) {
    companion object {
        const val BLOCK_TTL = 10L
    }
    fun check(contact: String) {
        if (blockRepository.isBlocked(contact)) {
            publisher.publish(OtpAbuseDetectedEvent(contact))
            throw TooManyOtpAttemptsException()
        }
    }

    fun blockIfExceedsLimit(contact: String, tryCount: Int) {
        if (tryCount >= OtpSession.MAX_TRY_COUNT) {
            blockRepository.block(contact, Duration.ofMinutes(BLOCK_TTL))
            publisher.publish(OtpAbuseDetectedEvent(contact))
            throw TooManyOtpAttemptsException()
        }
    }
}
