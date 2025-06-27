package `in`.ding.user.auth.infrastructure.messaging.kafka

import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AuthEventConsumer {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.auth}"],
        groupId = "auth-service-group",
        containerFactory = "authKafkaListenerContainerFactory"
    )
    fun consume(event: OtpRequestedEvent) {
        throw NotImplementedError()
    } }
