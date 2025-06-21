package `in`.ding.payment.application.rest

import `in`.ding.payment.application.dto.command.AuthCommand
import `in`.ding.payment.application.dto.command.CaptureCommand
import `in`.ding.payment.application.dto.http.AuthRequest
import `in`.ding.payment.application.service.PaymentAppService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RequestMapping("api/v1/sellers/{sellerExKey}/payments")
@RestController
class PaymentController(private val service: PaymentAppService) {
    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    fun postPaymentAuth(
        @PathVariable("sellerExKey") sellerExKey: UUID,
        @RequestBody request: AuthRequest
    ) {
        service.auth(
            AuthCommand.of(
                sellerExKey,
                request
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
