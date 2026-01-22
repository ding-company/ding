package `in`.ding.user.auth.domain.repository

import java.time.Duration

interface RedisOtpBlockRepository {
    fun block(contact: String, ttl: Duration)
    fun isBlocked(contact: String): Boolean
}
