package `in`.ding.user.auth.domain.repository

import java.time.Duration

interface RedisOtpBlockRepository {
    fun isBlocked(contact: String): Boolean
    fun block(contact: String, ttl: Duration)
}
