package `in`.ding.common.kafka

import `in`.ding.common.event.BaseEvent
import `in`.ding.common.event.EventTopicResolver
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class EventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val topicResolver: EventTopicResolver,
) {
    fun publish(event: BaseEvent) {
        kafkaTemplate.send(topicResolver.resolve(event.eventContext), event)
    }
}
