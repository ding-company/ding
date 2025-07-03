package `in`.ding.user.user.application.service

import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.consumer.UserRegisterByCustomerEventCommand
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.model.User
import `in`.ding.user.user.domain.model.enumerate.ContactType
import `in`.ding.user.user.domain.model.enumerate.UserNationality

class UserAppServiceImpl(private val repository: UserRepository) :
    UserAppService {
    override fun register(command: UserRegisterCommand) {
        throw NotImplementedError()
//        val user = User.register(
//            phoneNumber = command.phoneNumber,
//            email = command.email,
//            nationality = command.nationality
//        )
//        repository.save(user)
    }
    override fun registerFromCustomer(command: UserRegisterByCustomerEventCommand) {
        val user = User.makeTempUser(
            exKey = command.exKey,
            contact = command.phoneNumber!!,
            contactType = ContactType.PHONE_NUMBER,
            nationality = UserNationality.KR
        )
        repository.save(user)
    }
}
