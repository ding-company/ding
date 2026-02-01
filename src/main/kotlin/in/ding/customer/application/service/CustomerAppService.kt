package `in`.ding.customer.application.service

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.customer.application.dto.command.CustomerRegisterCommand
import `in`.ding.customer.domain.event.CustomerCreatedEvent
import `in`.ding.customer.domain.model.Customer
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerAppService(
    private val eventPublisher: EventPublisher,
) {
    @Transactional
    fun register(
        command: CustomerRegisterCommand,
    ) {
        val userExKey = UUID.randomUUID()
        val customer = Customer.createTemporary(
            userExKey = userExKey,
            sellerExKey = command.sellerExKey,
            phoneNumber = command.phoneNumber,
            name = command.name
        )
        customer.register()

        val event = CustomerCreatedEvent(customer.exKey, userExKey = userExKey, phoneNumber = command.phoneNumber)
        eventPublisher.publish(event)
    }
}
