package `in`.ding.payment.application.service

import `in`.ding.payment.application.dto.commandquery.CaptureCommand
import `in`.ding.payment.domain.entity.table.Payment
import `in`.ding.payment.infrastructure.db.repository.PaymentRepository

class PaymentAppServiceImpl(val repository: PaymentRepository) : PaymentAppService {
    override fun capture(command: CaptureCommand) {
        val payment = Payment.capture(sellerExKey = command.sellerExKey, amount = command.amount)
        repository.save(payment)
    }
}
