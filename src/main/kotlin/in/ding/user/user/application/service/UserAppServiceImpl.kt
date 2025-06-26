package `in`.ding.user.user.application.service

import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.consumer.UserRegisterByCustomerEventCommand
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.model.User
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import `in`.ding.user.user.domain.service.UserRegisterValidator

class UserAppServiceImpl(private val repository: UserRepository, private val userValidator: UserRegisterValidator) :
    UserAppService {
    override fun register(command: UserRegisterCommand) {
        userValidator.validateUniqueEmail(email = command.email)
        userValidator.validateUniquePhoneNumber(phoneNumber = command.phoneNumber)
        val user = User.register(
            phoneNumber = command.phoneNumber,
            email = command.email,
            nationality = command.nationality
        )
        repository.save(user)
    }
    override fun registerFromCustomer(command: UserRegisterByCustomerEventCommand) {
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
