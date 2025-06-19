package `in`.ding.user.infrastructure.db.repository

import `in`.ding.common.DomainID
import `in`.ding.user.infrastructure.db.table.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, DomainID>, UserCustomRepository {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun save(user: UserEntity)
}
