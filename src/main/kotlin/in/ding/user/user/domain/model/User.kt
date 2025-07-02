package `in`.ding.user.user.domain.model

import `in`.ding.user.user.domain.model.enumerate.ContactType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UserID,
    val exKey: UUID,
    val phoneNumber: PhoneNumber? = null,
    val email: Email? = null,
    val nationality: UserNationality = UserNationality.KR,
    val registeredAt: LocalDateTime
) {
    companion object {
        fun register(
            exKey: UUID = UUID.randomUUID(),
            contact: String,
            contactType: ContactType,
            nationality: UserNationality
        ): User {
            val phoneNumber = contact.takeUnless { contactType != ContactType.PHONE_NUMBER }?.let { PhoneNumber(it) }
            val email = contact.takeUnless { contactType != ContactType.EMAIL }?.let { Email(it) }
            return User(
                id = UserID.UNASSIGNED,
                exKey = exKey,
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality,
                registeredAt = LocalDateTime.now()
            )
        }
    }
}
