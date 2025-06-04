package `in`.ding.user.infrastructure.db.repository

import `in`.ding.user.domain.model.User

interface UserCustomRepository {
    fun save(user: User): User
}
