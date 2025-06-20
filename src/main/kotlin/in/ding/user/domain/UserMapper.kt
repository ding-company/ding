package `in`.ding.user.domain

import `in`.ding.common.DomainID
import `in`.ding.common.ErrorMessage
import `in`.ding.user.domain.model.User
import `in`.ding.user.infrastructure.db.table.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDomain(entity: UserEntity): User {
        return User(
            DomainID(requireNotNull(entity.id) { ErrorMessage.ID_IS_NULL }),
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
