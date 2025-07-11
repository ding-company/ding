package `in`.ding.user.user.application.service

import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.consumer.UserRegisterByCustomerEventCommand
import `in`.ding.user.user.application.dto.http.UserResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
interface UserAppService {
    fun getByExKey(exKey: UUID): UserResponse
    fun register(command: UserRegisterCommand)
    fun registerFromCustomer(command: UserRegisterByCustomerEventCommand)
}
