package `in`.ding.user.user.domain.model

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
            phoneNumber: String?,
            email: String?,
            nationality: UserNationality
        ): User {
            val phoneNumberValue = phoneNumber?.let { PhoneNumber(phoneNumber) }
            val emailValue = email?.let { Email(email) }
            return User(
                id = UserID.UNASSIGNED,
                exKey = exKey,
                phoneNumber = phoneNumberValue,
                email = emailValue,
                nationality = nationality,
                registeredAt = LocalDateTime.now()
            )
        }
    }
}
