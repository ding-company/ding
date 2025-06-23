package `in`.ding.user.infrastructure.db.repository

import `in`.ding.user.domain.UserRepository
import `in`.ding.user.domain.model.User
import `in`.ding.user.infrastructure.mapper.UserMapper

class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository,
    private val mapper: UserMapper
) : UserRepository {
    override fun save(domain: User): User {
        val entity = mapper.toEntity(domain).also {
            if (domain.id.isAssigned()) {
                it.id = domain.id.value
            }
        }
        val saved = jpaRepository.save(entity)
        return mapper.toDomain(saved)
    }

    override fun findByEmail(email: String): User? {
        return jpaRepository.findByEmail(email)?.let { entity -> mapper.toDomain(entity) }
    }

    override fun findByPhoneNumber(phoneNumber: String): User? {
        return jpaRepository.findByPhoneNumber(phoneNumber)?.let { entity -> mapper.toDomain(entity) }
    }
}
