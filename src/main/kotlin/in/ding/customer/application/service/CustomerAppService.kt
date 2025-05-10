package `in`.ding.customer.application.service

import `in`.ding.customer.application.dto.querycommand.CustomerRegisterCommand
import `in`.ding.user.domain.service.UserCommandService
import `in`.ding.user.domain.service.dto.UserRegisterDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CustomerAppService(
    private val userCommandService: UserCommandService,
) {
    @Transactional
    fun register(
        command: CustomerRegisterCommand,
    ) {
        userCommandService.register(
            dto = UserRegisterDTO(
                phoneNumber = command.phoneNumber,
                name = command.name,
                nationality = command.nationality,
                email = command.email,
            )
        )
    }
}
