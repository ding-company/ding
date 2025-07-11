package `in`.ding.user.auth.domain.repository

import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
interface RedisOtpBlockRepository {
    fun isBlocked(contact: String): Boolean
    fun block(contact: String, ttl: Duration)
}
