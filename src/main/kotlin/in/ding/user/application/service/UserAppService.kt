package `in`.ding.user.application.service

import `in`.ding.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.domain.model.User
import `in`.ding.user.domain.service.UserRegisterValidator
import `in`.ding.user.infrastructure.db.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserAppService(private val repository: UserRepository, private val userValidator: UserRegisterValidator) {
    fun register(command: UserRegisterCommand) {
        userValidator.validateUniqueEmail(email = command.email)
        userValidator.validateUniquePhoneNumber(phoneNumber = command.phoneNumber)
        val user = User.register(
            phoneNumber = command.phoneNumber,
            email = command.email,
            nationality = command.nationality
        )
        repository.save(user)
    }
}
