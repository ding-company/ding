package `in`.ding.user.domain.model

import `in`.ding.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime
import java.util.*
data class User(
    val exKey: UUID,
    val phoneNumber: String? = null,
    val email: String? = null,
    val nationality: UserNationality = UserNationality.KR,
    val isDeleted: Boolean = false,
    val deletedAt: LocalDateTime? = null,
    val registeredAt: LocalDateTime
) {
    companion object {
        fun register(
            phoneNumber: String?,
            email: String?,
            nationality: UserNationality
        ): User {
            return User(
                exKey = UUID.randomUUID(),
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality,
                registeredAt = LocalDateTime.now()
            )
        }
    }
}
