package `in`.ding.user.domain.service

import `in`.ding.user.domain.model.User
import `in`.ding.user.domain.service.dto.UserRegisterDTO
import `in`.ding.user.infrastructure.db.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val repository: UserRepository
) {
    fun register(dto: UserRegisterDTO): User {
        val user = User.register(
            repository = repository,
            phoneNumber = dto.phoneNumber,
            email = dto.email,
            nationality = dto.nationality
        )
        return repository.save(user = user)
    }
}
