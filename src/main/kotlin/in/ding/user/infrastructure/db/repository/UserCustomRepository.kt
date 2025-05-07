package `in`.ding.user.infrastructure.db.repository

import `in`.ding.user.domain.entity.User

interface UserCustomRepository {
    fun save(user: User): User
}
