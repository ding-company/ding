package `in`.ding.payment.application.service

import `in`.ding.customer.domain.CustomerRepository
import `in`.ding.customer.domain.model.Customer
import `in`.ding.payment.domain.event.PaymentAuthEvent
import `in`.ding.payment.domain.event.PaymentAuthStartedEvent
import `in`.ding.payment.infrastructure.kafka.messaging.PaymentEventPublisher
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentAuthorizationHandler(
    private val customerRepository: CustomerRepository,
    private val eventPublisher: PaymentEventPublisher
) {
    fun handle(event: PaymentAuthStartedEvent) {
        event.customerPhoneNumber ?: return
        var customer = customerRepository.findByPhoneNumber(event.customerPhoneNumber)
        if (customer == null) {
            val userExKey = UUID.randomUUID()
            customer = Customer.createTemporary(
                userExKey = userExKey,
                sellerExKey = event.sellerExKey,
                phoneNumber = event.customerPhoneNumber,
                name = null
            )
        }
        customerRepository.save(customer)
        eventPublisher.publish(
            PaymentAuthEvent.of(
                customerExKey = customer.exKey,
                event
            )
        )
    }
}
