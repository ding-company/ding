package `in`.ding.user.auth.infrastructure.redis

import `in`.ding.user.auth.domain.RedisOtpBlockRepository
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

class RedisOtpBlockRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : RedisOtpBlockRepository {
    override fun isBlocked(contact: String): Boolean {
        return redisTemplate.hasKey("otp:lock:$contact") == true
    }

    override fun block(contact: String, ttl: Duration) {
        redisTemplate.opsForValue().set("otp:lock:$contact", "1", ttl)
    }
}
