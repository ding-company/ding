package `in`.ding.user.auth.application.service.policy

import `in`.ding.common.kafka.EventPublisher
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.OtpBlacklistedException
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.repository.RedisBlacklistRepository
import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import org.springframework.stereotype.Component

@Component
class OtpAvailabilityGuard(
    private val blockRepository: RedisOtpBlockRepository,
    private val blacklistRepository: RedisBlacklistRepository,
    private val publisher: EventPublisher,
) {
    fun check(contact: String) =
        when {
            blockRepository.isBlocked(contact) -> {
                publisher.publish(OtpAbuseDetectedEvent(contact))
                throw TooManyOtpAttemptsException()
            }
            blacklistRepository.isBlacklisted(contact) ->
                throw OtpBlacklistedException()
            else -> Unit
        }
}
