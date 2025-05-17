package `in`.ding.payment.application.service

import `in`.ding.payment.application.dto.commandquery.AuthCommand
import `in`.ding.payment.application.dto.commandquery.CaptureCommand
import org.springframework.stereotype.Service

@Service
interface PaymentAppService {
    fun auth(command: AuthCommand)
    fun capture(command: CaptureCommand)
}
