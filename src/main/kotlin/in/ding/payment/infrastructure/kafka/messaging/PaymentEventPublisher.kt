package `in`.ding.payment.infrastructure.kafka.messaging

import `in`.ding.payment.domain.event.PaymentEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PaymentEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val topic = "payment.events"

    fun publish(event: PaymentEvent) {
        kafkaTemplate.send(topic, event)
    }
}
