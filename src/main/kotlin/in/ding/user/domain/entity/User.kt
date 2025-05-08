package `in`.ding.user.domain.entity

import `in`.ding.common.exception.BadRequestException
import `in`.ding.user.domain.entity.enumerate.UserNationality
import `in`.ding.user.infrastructure.db.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

class User private constructor(
    private val repository: UserRepository,
    val id: Long? = null,
    val exKey: UUID,
    val phoneNumber: String? = null,
    val email: String? = null,
    val name: String? = null,
    val nationality: UserNationality = UserNationality.KR,
    val isDeleted: Boolean = false,
    val deletedAt: LocalDateTime? = null,
    val registeredAt: LocalDateTime
) {
    companion object {
        fun register(
            repository: UserRepository,
            phoneNumber: String?,
            email: String?,
            name: String?,
            nationality: UserNationality,
        ): User {
            val user = User(
                repository = repository,
                id = null,
                exKey = UUID.randomUUID(),
                phoneNumber = phoneNumber,
                email = email,
                name = name,
                nationality = nationality,
                registeredAt = LocalDateTime.now()
            )
            validate(user)
            return user
        }

        private fun validate(user: User) {
            user.phoneNumber?.let { phone ->
                if (user.repository.findByPhoneNumber(phone) != null) {
                    throw BadRequestException("Phone number '$phone' is already in use.")
                }
            }

            user.email?.let { email ->
                if (user.repository.findByEmail(email) != null) {
                    throw BadRequestException("Email '$email' is already in use.")
                }
            }
        }
    }
}
