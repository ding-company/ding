package `in`.ding.user.infrastructure.messaging.kafka

import `in`.ding.customer.domain.event.CustomerCreatedEvent
import `in`.ding.user.application.dto.consumer.UserRegisterByCustomerEventCommand
import `in`.ding.user.application.service.UserAppService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CustomerEventConsumer(
    private val userService: UserAppService
) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.customer}"],
        groupId = "user-service-group",
        containerFactory = "userKafkaListenerContainerFactory"
    )
    fun consume(event: CustomerCreatedEvent) {
        val command = UserRegisterByCustomerEventCommand(
            exKey = event.userExKey,
            phoneNumber = event.phoneNumber,
            name = null,
        )
        userService.registerFromCustomer(command)
    }
}
