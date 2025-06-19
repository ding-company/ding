package `in`.ding.point.infrastructure.kafka

import `in`.ding.customer.domain.event.CustomerCreatedEvent
import `in`.ding.point.application.service.PointAppService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PointEventConsumer(
    private val pointService: PointAppService
) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.customer}"],
        groupId = "point-service-group",
        containerFactory = "pointKafkaListenerContainerFactory"
    )
    fun consume(event: CustomerCreatedEvent) {
        pointService.initPoint(customerExKey = event.exKey)
    }
}
