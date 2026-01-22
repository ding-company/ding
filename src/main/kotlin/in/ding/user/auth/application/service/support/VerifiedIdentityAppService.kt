package `in`.ding.user.auth.application.service.support

import `in`.ding.user.auth.application.expiry.ExpiryResolver
import `in`.ding.user.auth.domain.model.VerifiedIdentity
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.auth.domain.repository.RedisVerifiedIdentityRepository
import `in`.ding.user.user.domain.model.ContactPolicy
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import org.springframework.stereotype.Component

@Component
class VerifiedIdentityAppService(
    private val contactPolicy: ContactPolicy,
    private val verifiedIdentityRepository: RedisVerifiedIdentityRepository,
    private val expiryResolver: ExpiryResolver
) {
    fun create(
        contact: String,
        nationality: UserNationality,
    ): VerifiedIdentity {
        val contactType = contactPolicy.determineContactType(nationality)

        val identity = VerifiedIdentity.Companion.generate(
            contact = contact,
            contactType = contactType,
            nationality = nationality
        )

        val verifiedIdentityLifeTime = DomainLifetime.VERIFIED_IDENTITY
        val ttl = expiryResolver.resolve(verifiedIdentityLifeTime)

        verifiedIdentityRepository.saveVerifiedIdentity(contact, identity, ttl)
        return identity
    }
}
