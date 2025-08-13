package `in`.ding.user.user.domain.model

import `in`.ding.user.user.domain.model.enumerate.ContactType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UserID,
    val exKey: UUID,
    val phoneNumber: PhoneNumber? = null,
    val email: Email? = null,
    val nationality: UserNationality = UserNationality.KR,
    var status: UserStatus,
    val registeredAt: LocalDateTime?
) {
    companion object {
        fun makeTempUser(
            exKey: UUID = UUID.randomUUID(),
            contact: String,
            contactType: ContactType,
            nationality: UserNationality
        ): User {
            val phoneNumber = if (contactType == ContactType.PHONE_NUMBER) PhoneNumber(contact) else null
            val email = if (contactType == ContactType.EMAIL) Email(contact) else null
            return User(
                id = UserID.UNASSIGNED,
                exKey = exKey,
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality,
                status = UserStatus.TEMPORARY,
                registeredAt = LocalDateTime.now()
            )
        }
    }
    fun register() {
        this.status = UserStatus.REGISTERED
    }
}
