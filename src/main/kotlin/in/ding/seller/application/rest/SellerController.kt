package `in`.ding.seller.application.rest

import `in`.ding.seller.application.dto.SellerRegisterCommand
import `in`.ding.seller.application.dto.SellerRegisterRequest
import `in`.ding.seller.application.service.SellerRegisterService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("api/v1/sellers")
@RestController
class SellerController(private val service: SellerRegisterService) {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun postCustomer(
        @RequestBody requestBody: SellerRegisterRequest
    ) {
        service.register(
            command = SellerRegisterCommand(userExKey = UUID.randomUUID(), storeName = requestBody.storeName)
        )
    }
}
