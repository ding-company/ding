package `in`.ding.payment.infrastructure.kafka.messaging

import `in`.ding.common.kafka.KafkaCommonConfig
import `in`.ding.payment.domain.event.PaymentEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class PaymentKafkaProducerConfig {

    @Bean
    fun paymentProducerFactory(config: KafkaCommonConfig): ProducerFactory<String, PaymentEvent> {
        return DefaultKafkaProducerFactory(config.producerConfigs())
    }

    @Bean
    fun paymentKafkaTemplate(config: KafkaCommonConfig): KafkaTemplate<String, PaymentEvent> {
        return KafkaTemplate(paymentProducerFactory(config))
    }
}
