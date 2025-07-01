package `in`.ding.user.user.domain

import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRedisRepository {
    fun saveTemporaryUser(contact: String, userData: User)

    fun findTemporaryUser(contact: String): Any?

    fun deleteTemporaryUser(contact: String)
}
