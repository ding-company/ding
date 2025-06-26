package `in`.ding.user.user.application.service

import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.consumer.UserRegisterByCustomerEventCommand
import org.springframework.stereotype.Service

@Service
interface UserAppService {
    fun register(command: UserRegisterCommand)
    fun registerFromCustomer(command: UserRegisterByCustomerEventCommand)
}
