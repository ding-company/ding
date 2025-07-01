package `in`.ding.user.auth.infrastructure.messaging.kafka

import `in`.ding.user.auth.application.handler.OtpIssuanceHandler
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AuthEventConsumer(private val handler: OtpIssuanceHandler) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.auth}"],
        groupId = "auth-service-group",
        containerFactory = "authKafkaListenerContainerFactory"
    )
    fun consume(event: OtpRequestedEvent) {
        handler.handle(event)
    } }
