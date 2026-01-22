package `in`.ding.user.auth.domain.repository

import `in`.ding.user.auth.domain.model.VerifiedIdentity

interface RedisVerifiedIdentityRepository {
    fun saveVerifiedIdentity(contact: String, value: VerifiedIdentity)

    fun findVerifiedIdentity(contact: String): VerifiedIdentity?

    fun deleteVerifiedIdentity(contact: String)
}
