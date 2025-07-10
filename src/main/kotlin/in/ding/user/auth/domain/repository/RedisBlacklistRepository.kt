package `in`.ding.user.auth.domain.repository

import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
interface RedisBlacklistRepository {
    fun blacklist(token: String, ttl: Duration)
    fun isBlacklisted(token: String): Boolean
}
