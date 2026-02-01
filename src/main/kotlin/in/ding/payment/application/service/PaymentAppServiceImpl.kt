package `in`.ding.payment.application.service

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.common.infra.http.exception.NotFoundException
import `in`.ding.payment.application.dto.command.AuthCommand
import `in`.ding.payment.application.dto.command.CaptureCommand
import `in`.ding.payment.domain.PaymentRepository
import `in`.ding.payment.domain.event.PaymentAuthStartedEvent
import `in`.ding.payment.domain.model.Payment
import org.springframework.stereotype.Service

@Service
class PaymentAppServiceImpl(
    private val repository: PaymentRepository,
    private val eventPublisher: EventPublisher,
) : PaymentAppService {
    override fun auth(command: AuthCommand) {
        val payment = Payment.authorize(sellerExKey = command.sellerExKey, amount = command.amount)
        val savedPayment = repository.save(payment)
        eventPublisher.publish(PaymentAuthStartedEvent.of(savedPayment.exKey, command))
    }

    override fun capture(command: CaptureCommand) {
        val payment = repository.findByExKey(command.exKey) ?: throw NotFoundException()
        payment.capture()
        repository.save(payment)
    }
}
