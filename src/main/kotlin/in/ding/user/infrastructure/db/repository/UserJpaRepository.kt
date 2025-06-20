package `in`.ding.user.infrastructure.db.repository

import `in`.ding.common.ID
import `in`.ding.user.infrastructure.db.table.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, ID> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
}
