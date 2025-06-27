package `in`.ding.user.auth.infrastructure.messaging.kafka

import `in`.ding.common.kafka.KafkaCommonConfig
import `in`.ding.user.auth.domain.event.AuthEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class AuthKafkaProducerConfig {

    @Bean
    fun authProducerFactory(config: KafkaCommonConfig): ProducerFactory<String, AuthEvent> {
        return DefaultKafkaProducerFactory(config.producerConfigs())
    }

    @Bean
    fun authKafkaTemplate(config: KafkaCommonConfig): KafkaTemplate<String, AuthEvent> {
        return KafkaTemplate(authProducerFactory(config))
    }
}
