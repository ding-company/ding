package `in`.ding.payment.infrastructure.kafka.messaging

import `in`.ding.common.infra.kafka.BaseKafkaConsumerConfig
import `in`.ding.payment.domain.event.PaymentAuthStartedEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class PaymentKafkaConsumerConfig(
    private val base: BaseKafkaConsumerConfig
) {

    @Value("\${kafka.consumer.payment.group-id:payment-service-group}")
    private lateinit var groupId: String

    @Bean
    fun paymentAuthStartedKafkaListenerContainerFactory():
        ConcurrentKafkaListenerContainerFactory<String, PaymentAuthStartedEvent> {
        return base.kafkaListenerContainerFactory(groupId, PaymentAuthStartedEvent::class.java)
    }
}
