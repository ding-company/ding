package `in`.ding.payment.infrastructure.kafka.messaging

import `in`.ding.payment.application.service.PaymentAuthorizationHandler
import `in`.ding.payment.domain.event.PaymentAuthStartedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentEventConsumer(
    private val handler: PaymentAuthorizationHandler
) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.payment}"],
        groupId = "payment-service-group",
        containerFactory = "paymentKafkaListenerContainerFactory"
    )
    fun consume(event: PaymentAuthStartedEvent) {
        handler.handle(event)
    }
}
