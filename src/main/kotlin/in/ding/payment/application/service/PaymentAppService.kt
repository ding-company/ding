package `in`.ding.payment.application.service

import `in`.ding.payment.application.dto.command.AuthCommand
import `in`.ding.payment.application.dto.command.CaptureCommand

interface PaymentAppService {
    fun auth(command: AuthCommand)
    fun capture(command: CaptureCommand)
}
