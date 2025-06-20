package `in`.ding.payment.application.rest

import `in`.ding.payment.application.dto.commandquery.AuthCommand
import `in`.ding.payment.application.dto.commandquery.CaptureCommand
import `in`.ding.payment.application.service.PaymentAppService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID

@RequestMapping("api/v1/sellers/{sellerExKey}/payments")
@RestController
class PaymentController(private val service: PaymentAppService) {
    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    fun postPaymentAuth(@PathVariable("sellerExKey") sellerExKey: UUID) {
        service.auth(
            AuthCommand(
                sellerExKey,
                BigDecimal.TEN
            )
        )
    }

    @PostMapping("/{exKey}/capture")
    @ResponseStatus(HttpStatus.CREATED)
    fun postPaymentCapture(@PathVariable("sellerExKey") sellerExKey: UUID) {
        service.capture(
            CaptureCommand(
                sellerExKey,
            )
        )
    }
}
