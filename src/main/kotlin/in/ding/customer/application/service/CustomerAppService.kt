package `in`.ding.customer.application.service

import `in`.ding.customer.application.dto.PostCustomerRequest
import `in`.ding.customer.domain.service.CustomerCommandService
import `in`.ding.customer.domain.service.dto.CustomerRegisterDTO
import `in`.ding.point.domain.service.PointCommandService
import `in`.ding.user.domain.service.UserCommandService
import `in`.ding.user.domain.service.dto.UserRegisterDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CustomerAppService(
    private val userCommandService: UserCommandService,
    private val customerCommandService: CustomerCommandService,
    private val pointCommandService: PointCommandService
) {
    @Transactional
    fun register(
        request: PostCustomerRequest,
        sellerExternalKey: String,
    ) {
        val user = userCommandService.register(
            dto = UserRegisterDTO(
                phoneNumber = request.phoneNumber,
                name = request.name,
                nationality = request.nationality,
                email = request.email,
            )
        )
        customerCommandService.register(
            dto = CustomerRegisterDTO(
                phoneNumber = request.phoneNumber,
                name = request.name,
                nationality = request.nationality,
                email = request.email,
                userExKey = user.exKey,
            )
        )
        pointCommandService.create(customerExternalKey = customerExternalKey)
    }
}
