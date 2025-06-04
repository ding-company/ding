package `in`.ding.user.infrastructure.db.repository

import `in`.ding.user.domain.model.User
import `in`.ding.user.infrastructure.db.table.UserEntity
import org.springframework.stereotype.Repository

@Repository
class UserCustomerRepositoryImpl : UserCustomRepository {
    override fun save(user: User): User {
        convertEntityToJPAEntity(user = user)
        // TODO 구현
        return user
    }
    fun convertEntityToJPAEntity(
        user: User
    ): UserEntity {
        return UserEntity(
            exKey = user.exKey,
            phoneNumber = user.phoneNumber,
            email = user.email,
            nationality = user.nationality,
            registeredAt = user.registeredAt
        )
    }
}
