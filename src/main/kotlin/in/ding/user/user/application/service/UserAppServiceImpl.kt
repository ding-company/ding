package `in`.ding.user.user.application.service

import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.consumer.UserRegisterByCustomerEventCommand
import `in`.ding.user.user.application.dto.http.UserResponse
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.exception.NotFoundUserException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserAppServiceImpl(private val repository: UserRepository) :
    UserAppService {
    override fun getByExKey(exKey: UUID): UserResponse {
        val user = repository.findByExKey(exKey) ?: throw NotFoundUserException()
        return UserResponse(user.exKey, user.status)
    }
    override fun register(command: UserRegisterCommand) {
        val user = repository.findByExKey(command.userExKey) ?: throw NotFoundUserException()
//        user.register()
        throw NotImplementedError()
    }
    override fun registerFromCustomer(command: UserRegisterByCustomerEventCommand) {
//        val user = User.makeTempUser(
//            exKey = command.exKey,
//            contact = command.phoneNumber!!,
//            contactType = ContactType.PHONE_NUMBER,
//            nationality = UserNationality.KR
//        )
//        repository.save(user)
    }
}
