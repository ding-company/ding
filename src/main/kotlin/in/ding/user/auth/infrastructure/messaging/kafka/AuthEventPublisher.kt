package `in`.ding.user.auth.infrastructure.messaging.kafka

import `in`.ding.user.auth.domain.event.AuthEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
@Component
class AuthEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val topic = "auth.events"

    fun publish(event: AuthEvent) {
        kafkaTemplate.send(topic, event)
    }
}
