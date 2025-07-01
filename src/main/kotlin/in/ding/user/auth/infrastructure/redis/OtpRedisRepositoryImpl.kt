package `in`.ding.user.auth.infrastructure.redis

import `in`.ding.user.auth.domain.RedisOtpRepository
import `in`.ding.user.auth.domain.model.OtpSession
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

class OtpRedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, OtpSession>
) : RedisOtpRepository {
    companion object {
        private const val OTP_KEY_PREFIX = "otp:"
        private const val OTP_TTL = 5L
    }

    override fun saveOtp(contact: String, otp: OtpSession) {
        redisTemplate.opsForValue().set(
            "$OTP_KEY_PREFIX$contact",
            otp,
            Duration.ofMinutes(OTP_TTL)
        )
    }

    override fun findOtp(contact: String): OtpSession? {
        return redisTemplate.opsForValue().get("$OTP_KEY_PREFIX$contact")
    }

    override fun deleteOtp(contact: String) {
        redisTemplate.delete("$OTP_KEY_PREFIX$contact")
    }
}
