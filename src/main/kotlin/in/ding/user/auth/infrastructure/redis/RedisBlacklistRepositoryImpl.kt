package `in`.ding.user.auth.infrastructure.redis

import `in`.ding.user.auth.domain.repository.RedisBlacklistRepository
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

class RedisBlacklistRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : RedisBlacklistRepository {
    companion object {
        const val PREFIX = "bl:"
    }
    override fun blacklist(token: String, ttl: Duration) {
        redisTemplate.opsForValue().set("$PREFIX$token", "1", ttl)
    }

    override fun isBlacklisted(token: String): Boolean {
        return redisTemplate.hasKey("$PREFIX:$token") == true
    }
}
