package `in`.ding.user.domain.model

import `in`.ding.common.DomainID
import `in`.ding.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime
import java.util.*

data class User(
    val id: DomainID,
    val exKey: UUID,
    val phoneNumber: String? = null,
    val email: String? = null,
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
            return User(
                id = DomainID.UNASSIGNED,
                exKey = exKey,
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality,
                registeredAt = LocalDateTime.now()
            )
        }
    }
}
