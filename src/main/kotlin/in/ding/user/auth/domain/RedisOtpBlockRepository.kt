package `in`.ding.user.auth.domain

import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
interface RedisOtpBlockRepository {
    fun isBlocked(contact: String): Boolean
    fun block(contact: String, ttl: Duration)
}
