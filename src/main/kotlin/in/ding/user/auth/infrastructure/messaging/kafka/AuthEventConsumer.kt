package `in`.ding.user.auth.infrastructure.messaging.kafka

import `in`.ding.user.auth.application.service.OtpServiceImpl
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

// TODO 사용안함 차후 제거
@Component
class AuthEventConsumer(private val service: OtpServiceImpl) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.auth}"],
        groupId = "auth-service-group",
        containerFactory = "authKafkaListenerContainerFactory"
    )
    fun consume(event: OtpRequestedEvent) {
        return
    } }
