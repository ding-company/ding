package `in`.ding.point.infrastructure.kafka

import `in`.ding.customer.domain.event.CustomerCreatedEvent
import `in`.ding.point.application.handler.PointInitHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
@Component
class CustomerEventConsumer(
    private val pointInitHandler: PointInitHandler
) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.customer}"],
        groupId = "point-service-group",
        containerFactory = "pointKafkaListenerContainerFactory"
    )
    fun handleCustomerCreated(event: CustomerCreatedEvent) {
        pointInitHandler.initPoint(customerExKey = event.exKey)
    }
}
