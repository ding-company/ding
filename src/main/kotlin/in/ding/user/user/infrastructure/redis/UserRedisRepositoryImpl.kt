package `in`.ding.user.user.infrastructure.redis

import `in`.ding.user.user.domain.UserRedisRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRedisRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : UserRedisRepository {
    companion object {
        private const val USER_KEY_PREFIX = "user:"
        private const val USER_TTL = 5L
    }
    override fun saveTemporaryUser(contact: String, userData: String) {
        redisTemplate.opsForValue().set(
            "$USER_KEY_PREFIX$contact",
            userData,
            USER_TTL
        )
    }

    // 유저 정보 조회
    override fun findTemporaryUser(contact: String): String? {
        return redisTemplate.opsForValue().get("$USER_KEY_PREFIX$contact")
    }

    // 유저 정보 삭제 (가입 완료 시)
    override fun deleteTemporaryUser(contact: String) {
        redisTemplate.delete("$USER_KEY_PREFIX$contact")
    }
}
