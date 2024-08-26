package `in`.ding.user.domain.service

import `in`.ding.user.domain.entity.User
import `in`.ding.user.domain.service.dto.UserRegisterDTO
import `in`.ding.user.infrastructure.db.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val userRepository: UserRepository
) {
    fun register(dto: UserRegisterDTO) {
        User.register(
            repository = userRepository,
            phoneNumber = dto.phoneNumber,
            email = dto.email,
            name = dto.name
        )
    }
}
