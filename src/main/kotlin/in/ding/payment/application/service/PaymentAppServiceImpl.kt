package `in`.ding.payment.application.service

import `in`.ding.common.exception.NotFoundException
import `in`.ding.payment.application.dto.commandquery.AuthCommand
import `in`.ding.payment.application.dto.commandquery.CaptureCommand
import `in`.ding.payment.domain.PaymentCustomRepository
import `in`.ding.payment.domain.entity.Payment

class PaymentAppServiceImpl(val repository: PaymentCustomRepository) : PaymentAppService {
    override fun auth(command: AuthCommand) {
        val payment = Payment.authorize(sellerExKey = command.sellerExKey, amount = command.amount)
        repository.save(payment)
    }

    override fun capture(command: CaptureCommand) {
        val payment = repository.findByExKey(command.exKey) ?: throw NotFoundException()
        payment.capture()
        repository.save(payment)
    }
}
