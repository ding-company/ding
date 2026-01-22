package `in`.ding.user.auth.infrastructure.redis

import `in`.ding.user.auth.domain.repository.RedisOtpBlockRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisOtpBlockRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : RedisOtpBlockRepository {
    companion object {
        private const val KEY = "otp:block:"
    }

    override fun block(contact: String, ttl: Duration) {
        redisTemplate.opsForValue().set("$KEY$contact", "1", ttl)
    }

    override fun isBlocked(contact: String): Boolean {
        return redisTemplate.hasKey("$KEY$contact") == true
    }
}
