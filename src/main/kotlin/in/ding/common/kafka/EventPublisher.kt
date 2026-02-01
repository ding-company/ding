package `in`.ding.common.kafka

import `in`.ding.common.event.BaseEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class EventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun publish(event: BaseEvent) {
        kafkaTemplate.send(event.eventContext.toString(), event)
    }
}
