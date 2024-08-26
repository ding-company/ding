package `in`.ding.user.infrastructure.db.repository

import `in`.ding.user.domain.entity.table.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun save(user: UserEntity)
}
