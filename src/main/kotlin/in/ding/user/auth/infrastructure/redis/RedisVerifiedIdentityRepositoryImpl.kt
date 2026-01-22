package `in`.ding.user.auth.infrastructure.redis

import `in`.ding.user.auth.domain.model.VerifiedIdentity
import `in`.ding.user.auth.domain.repository.RedisVerifiedIdentityRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisVerifiedIdentityRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, VerifiedIdentity>
) : RedisVerifiedIdentityRepository {
    companion object {
        private const val USER_KEY_PREFIX = "user:"

        // TODO ttl은 서비스에서 정하도록 변경해야함
        private val USER_TTL = Duration.ofMinutes(60L)
    }
    override fun saveVerifiedIdentity(contact: String, value: VerifiedIdentity) {
        redisTemplate.opsForValue().set(
            "$USER_KEY_PREFIX$contact",
            value,
            USER_TTL
        )
    }

    // 유저 정보 조회
    override fun findVerifiedIdentity(contact: String): VerifiedIdentity? {
        return redisTemplate.opsForValue().get("$USER_KEY_PREFIX$contact")
    }

    // 유저 정보 삭제 (가입 완료 시)
    override fun deleteVerifiedIdentity(contact: String) {
        redisTemplate.delete("$USER_KEY_PREFIX$contact")
    }
}
