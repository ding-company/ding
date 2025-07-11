package `in`.ding.user.user.infrastructure.db.repository

import `in`.ding.common.ID
import `in`.ding.user.user.infrastructure.db.table.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserJpaRepository : JpaRepository<UserEntity, ID> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun findByExKey(exKey: UUID): UserEntity?
}
