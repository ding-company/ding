package `in`.ding.user.auth.domain.repository

import `in`.ding.user.auth.domain.model.VerifiedIdentity
import java.time.Duration

interface RedisVerifiedIdentityRepository {
    fun saveVerifiedIdentity(contact: String, value: VerifiedIdentity, ttl: Duration)

    fun findVerifiedIdentity(contact: String): VerifiedIdentity?

    fun deleteVerifiedIdentity(contact: String)
}
