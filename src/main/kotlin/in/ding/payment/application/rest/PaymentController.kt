package `in`.ding.payment.application.rest

import `in`.ding.payment.application.dto.commandquery.CaptureCommand
import `in`.ding.payment.application.service.PaymentAppService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID

@RequestMapping("api/v1/sellers/{sellerExKey}/payments")
@RestController
class PaymentController(private val service: PaymentAppService) {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun postCustomer() {
        service.capture(
            CaptureCommand(
                UUID.randomUUID(),
                BigDecimal.TEN
            )
        )
    }
}
