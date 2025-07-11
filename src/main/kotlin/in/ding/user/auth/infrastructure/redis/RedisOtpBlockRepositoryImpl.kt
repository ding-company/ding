package `in`.ding.user.auth.infrastructure.redis

import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

class RedisOtpBlockRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : RedisOtpBlockRepository {
    companion object {
        private const val KEY = "otp:block:"
    }
    override fun isBlocked(contact: String): Boolean {
        return redisTemplate.hasKey("$KEY$contact") == true
    }

    override fun block(contact: String, ttl: Duration) {
        redisTemplate.opsForValue().set("$KEY$contact", "1", ttl)
    }
}
