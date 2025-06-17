package `in`.ding.customer.application.service

import `in`.ding.customer.application.dto.querycommand.CustomerRegisterCommand
import `in`.ding.customer.domain.event.CustomerCreatedEvent
import `in`.ding.customer.domain.model.Customer
import `in`.ding.customer.infrastructure.messaging.kafka.CustomerEventPublisher
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerAppService(
    private val eventPublisher: CustomerEventPublisher
) {
    @Transactional
    fun register(
        command: CustomerRegisterCommand,
    ) {
        val userExKey = UUID.randomUUID()
        val customer = Customer.register(userExKey = userExKey, phoneNumber = command.phoneNumber, name = command.name)

        val event = CustomerCreatedEvent(customer.exKey, userExKey = userExKey, phoneNumber = command.phoneNumber)
        eventPublisher.publish(event)
    }
}
