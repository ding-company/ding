package `in`.ding.payment.infrastructure.kafka.messaging

import `in`.ding.payment.application.handler.PaymentAuthorizationHandler
import `in`.ding.payment.domain.event.PaymentAuthStartedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentAuthStartedEventConsumer(
    private val handler: PaymentAuthorizationHandler
) {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.payment}"],
        groupId = "payment-service-group",
        containerFactory = "paymentAuthStartedKafkaListenerContainerFactory"
    )
    fun consume(event: PaymentAuthStartedEvent) {
        handler.handle(event)
    }
}
