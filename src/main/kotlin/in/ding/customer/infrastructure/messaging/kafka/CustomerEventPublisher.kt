package `in`.ding.customer.infrastructure.messaging.kafka

import `in`.ding.customer.domain.event.CustomerEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
@Component
class CustomerEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val topic = "customer.events"

    fun publish(event: CustomerEvent) {
        kafkaTemplate.send(topic, event)
    }
}
