package `in`.ding.payment.application.service

import `in`.ding.payment.application.dto.command.AuthCommand
import `in`.ding.payment.application.dto.command.CaptureCommand
import org.springframework.stereotype.Service

@Service
interface PaymentAppService {
    fun auth(command: AuthCommand)
    fun capture(command: CaptureCommand)
}
