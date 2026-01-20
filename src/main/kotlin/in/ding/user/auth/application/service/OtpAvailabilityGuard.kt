package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.domain.model.enumerate.OtpAvailability
import `in`.ding.user.auth.domain.repository.RedisBlacklistRepository
import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import org.springframework.stereotype.Component

@Component
class OtpAvailabilityGuard(
    private val blockRepository: RedisOtpBlockRepository,
    private val blacklistRepository: RedisBlacklistRepository
) {
    fun check(contact: String): OtpAvailability =
        when {
            blockRepository.isBlocked(contact) -> OtpAvailability.BLOCKED
            blacklistRepository.isBlacklisted(contact) -> OtpAvailability.BLACKLISTED
            else -> OtpAvailability.OK
        }
}
