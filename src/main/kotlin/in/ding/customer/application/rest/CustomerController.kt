package `in`.ding.customer.application.rest

import `in`.ding.customer.application.dto.command.CustomerRegisterCommand
import `in`.ding.customer.application.dto.http.PostCustomerRequest
import `in`.ding.customer.application.service.CustomerAppService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RequestMapping("api/v1/sellers/{sellerExKey}/customers")
@RestController
class CustomerController(private val service: CustomerAppService) {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun postCustomer(
        @RequestBody requestBody: PostCustomerRequest,
        @PathVariable("sellerExKey") sellerExKey: UUID,
    ) {
        service.register(command = CustomerRegisterCommand.of(requestBody, sellerExKey))
    }
}
