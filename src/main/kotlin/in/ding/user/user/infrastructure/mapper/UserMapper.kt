package `in`.ding.user.user.infrastructure.mapper

import `in`.ding.common.infra.http.ErrorMessage
import `in`.ding.user.user.domain.model.Email
import `in`.ding.user.user.domain.model.PhoneNumber
import `in`.ding.user.user.domain.model.User
import `in`.ding.user.user.domain.model.UserID
import `in`.ding.user.user.infrastructure.db.table.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDomain(entity: UserEntity): User {
        val phoneNumberValue = entity.phoneNumber?.let { PhoneNumber(it) }
        val emailValue = entity.email?.let { Email(it) }
        return User(
            UserID(requireNotNull(entity.id) { ErrorMessage.ID_IS_NULL }),
            exKey = entity.exKey,
            phoneNumber = phoneNumberValue,
            email = emailValue,
            nationality = entity.nationality,
            status = entity.status,
            registeredAt = entity.registeredAt,
        )
    }
    fun toEntity(domain: User): UserEntity {
        val entity = UserEntity(
            exKey = domain.exKey,
            phoneNumber = domain.phoneNumber?.value,
            email = domain.email?.value,
            nationality = domain.nationality,
            status = domain.status,
            registeredAt = domain.registeredAt,
        )
        if (domain.id.isAssigned()) {
            entity.id = domain.id.value
        }
        return entity
    }
}
