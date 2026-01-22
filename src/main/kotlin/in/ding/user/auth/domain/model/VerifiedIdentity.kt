package `in`.ding.user.auth.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import `in`.ding.user.user.domain.model.Email
import `in`.ding.user.user.domain.model.PhoneNumber
import `in`.ding.user.user.domain.model.enumerate.ContactType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime
import java.util.*

data class VerifiedIdentity(
    val exKey: UUID,
    val phoneNumber: PhoneNumber? = null,
    val email: Email? = null,
    val nationality: UserNationality = UserNationality.KR,
    val registeredAt: LocalDateTime?
) {
    companion object {
        fun generate(
            exKey: UUID = UUID.randomUUID(),
            contact: String,
            contactType: ContactType,
            nationality: UserNationality
        ): VerifiedIdentity {
            val phoneNumber = if (contactType == ContactType.PHONE_NUMBER) PhoneNumber(contact) else null
            val email = if (contactType == ContactType.EMAIL) Email(contact) else null
            return VerifiedIdentity(
                exKey = exKey,
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality,
                registeredAt = LocalDateTime.now()
            )
        }

        @Suppress("UnusedPrivateMember", "LongParameterList")
        @JsonCreator
        @JvmStatic
        private fun jsonCreator(
            exKey: UUID,
            phoneNumber: String?,
            email: String?,
            nationality: UserNationality,
            registeredAt: LocalDateTime?
        ): VerifiedIdentity {
            return VerifiedIdentity(
                exKey = exKey,
                phoneNumber = phoneNumber?.let { PhoneNumber(phoneNumber) },
                email = email?.let { Email(email) },
                nationality = nationality,
                registeredAt = registeredAt
            )
        }
    }
}
