package `in`.ding.customer.infrastructure.messaging.kafka

import `in`.ding.config.kafka.KafkaCommonConfig
import `in`.ding.customer.domain.event.CustomerEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class CustomerKafkaProducerConfig {

    @Bean
    fun customerProducerFactory(config: KafkaCommonConfig): ProducerFactory<String, CustomerEvent> {
        return DefaultKafkaProducerFactory(config.producerConfigs())
    }

    @Bean
    fun customerKafkaTemplate(config: KafkaCommonConfig): KafkaTemplate<String, CustomerEvent> {
        return KafkaTemplate(customerProducerFactory(config))
    }
}
