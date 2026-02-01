package `in`.ding.payment.application.handler

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.customer.domain.CustomerRepository
import `in`.ding.customer.domain.model.Customer
import `in`.ding.payment.domain.event.PaymentAuthStartedEvent
import `in`.ding.payment.domain.event.PaymentAuthedEvent
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PaymentAuthorizationHandler(
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) {
    fun handle(event: PaymentAuthStartedEvent) {
        event.customerPhoneNumber ?: return
        var customer = customerRepository.findByPhoneNumber(event.customerPhoneNumber)
        if (customer == null) {
            val userExKey = UUID.randomUUID()
            customer = Customer.Companion.createTemporary(
                userExKey = userExKey,
                sellerExKey = event.sellerExKey,
                phoneNumber = event.customerPhoneNumber,
                name = null
            )
        }
        customerRepository.save(customer)
        eventPublisher.publish(
            PaymentAuthedEvent.Companion.of(
                customerExKey = customer.exKey,
                event
            )
        )
    }
}
