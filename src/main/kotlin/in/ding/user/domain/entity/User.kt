package `in`.ding.user.domain.entity

import `in`.ding.common.exception.BadRequestException
import `in`.ding.user.domain.entity.enumerate.UserNationality
import `in`.ding.user.infrastructure.db.repository.UserRepository
import java.util.*

class User private constructor(
    private val repository: UserRepository,
    val externalKey: String,
    val phoneNumber: String? = null,
    val email: String? = null,
    val name: String? = null,
    val nationality: UserNationality = UserNationality.KR
) {
    companion object {
        fun register(
            repository: UserRepository,
            phoneNumber: String?,
            email: String?,
            name: String?,
        ): User {
            val user = User(
                repository = repository,
                externalKey = UUID.randomUUID().toString(),
                phoneNumber = phoneNumber,
                email = email,
                name = name,
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
