package `in`.ding.user.auth.domain.repository

import `in`.ding.user.auth.domain.model.VerifiedIdentity
import java.time.Duration
import java.util.UUID

interface RedisVerifiedIdentityRepository {
    fun saveVerifiedIdentity(exKey: UUID, value: VerifiedIdentity, ttl: Duration)

    fun findVerifiedIdentity(exKey: UUID): VerifiedIdentity?

    fun deleteVerifiedIdentity(exKey: UUID)
}
