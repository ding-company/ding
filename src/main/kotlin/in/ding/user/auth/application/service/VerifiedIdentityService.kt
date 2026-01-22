package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.domain.model.VerifiedIdentity
import `in`.ding.user.auth.domain.repository.RedisVerifiedIdentityRepository
import `in`.ding.user.user.domain.model.ContactPolicy
import org.springframework.stereotype.Component

@Component
class VerifiedIdentityService(
    private val contactPolicy: ContactPolicy,
    private val repository: RedisVerifiedIdentityRepository
) {
    fun create(command: OtpVerifyCommand): VerifiedIdentity {
        val contactType = contactPolicy.determineContactType(command.nationality)

        val identity = VerifiedIdentity.generate(
            contact = command.contact,
            contactType = contactType,
            nationality = command.nationality
        )

        repository.saveVerifiedIdentity(command.contact, identity)
        return identity
    }
}
