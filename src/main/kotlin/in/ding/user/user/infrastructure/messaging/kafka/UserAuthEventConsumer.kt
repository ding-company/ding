package `in`.ding.user.user.infrastructure.messaging.kafka

import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.user.application.handler.MakeTemporaryUserHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserAuthEventConsumer(
    private val handler: MakeTemporaryUserHandler
) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.auth}"],
        groupId = "user-service-group",
        containerFactory = "userKafkaListenerContainerFactory"
    )
    fun consumeOtpRequested(event: OtpRequestedEvent) {
        handler.handle(event)
    }
}
