package `in`.ding.user.user.infrastructure.mapper

import `in`.ding.common.ErrorMessage
import `in`.ding.user.user.domain.model.User
import `in`.ding.user.user.domain.model.UserID
import `in`.ding.user.user.infrastructure.db.table.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDomain(entity: UserEntity): User {
        return User(
            UserID(requireNotNull(entity.id) { ErrorMessage.ID_IS_NULL }),
            exKey = entity.exKey,
            phoneNumber = entity.phoneNumber,
            email = entity.email,
            nationality = entity.nationality,
            registeredAt = entity.registeredAt,
        )
    }
    fun toEntity(domain: User): UserEntity {
        val entity = UserEntity(
            exKey = domain.exKey,
            phoneNumber = domain.phoneNumber,
            email = domain.email,
            nationality = domain.nationality,
            registeredAt = domain.registeredAt,
        )
        if (domain.id.isAssigned()) {
            entity.id = domain.id.value
        }
        return entity
    }
}
