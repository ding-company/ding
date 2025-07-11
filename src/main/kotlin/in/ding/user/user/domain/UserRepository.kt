package `in`.ding.user.user.domain

import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository {
    fun save(domain: User): User
    fun findByPhoneNumber(phoneNumber: String): User?
    fun findByEmail(email: String): User?
    fun findByExKey(exKey: UUID): User?
}
