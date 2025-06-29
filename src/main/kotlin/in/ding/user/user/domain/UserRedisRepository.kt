package `in`.ding.user.user.domain

interface UserRedisRepository {
    fun saveTemporaryUser(contact: String, userData: String)

    fun findTemporaryUser(contact: String): String?

    fun deleteTemporaryUser(contact: String)
}
