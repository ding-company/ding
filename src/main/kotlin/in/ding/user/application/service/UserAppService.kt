package `in`.ding.user.application.service

import `in`.ding.user.application.consumer.dto.UserRegisterByCustomerEventCommand
import `in`.ding.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.domain.UserRepository
import `in`.ding.user.domain.model.User
import `in`.ding.user.domain.model.enumerate.UserNationality
import `in`.ding.user.domain.service.UserRegisterValidator
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
    fun registerFromCustomer(command: UserRegisterByCustomerEventCommand) {
        userValidator.validateUniquePhoneNumber(phoneNumber = command.phoneNumber)
        val user = User.register(
            exKey = command.exKey,
            phoneNumber = command.phoneNumber,
            email = null,
            nationality = UserNationality.KR
        )
        repository.save(user)
    }
}
