package `in`.ding.user.user.domain.model

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
    val status: UserStatus,
    val registeredAt: LocalDateTime?
) {
    companion object {
        fun register(
            exKey: UUID,
            phoneNumber: PhoneNumber?,
            email: Email?,
            nationality: UserNationality,
            registeredAt: LocalDateTime
        ): User {
            return User(
                id = UserID.UNASSIGNED,
                exKey = exKey,
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality,
                status = UserStatus.REGISTERED,
                registeredAt = registeredAt
            )
        }
    }
}
