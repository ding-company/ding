package `in`.ding.user.domain

import `in`.ding.user.domain.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    fun save(domain: User): User
    fun findByPhoneNumber(phoneNumber: String): User?
    fun findByEmail(email: String): User?
}
