package `in`.ding.common.infra.event

import `in`.ding.common.domain.event.BaseEvent
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
