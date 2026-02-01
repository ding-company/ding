package `in`.ding.user.auth.infrastructure.messaging.kafka

import `in`.ding.common.kafka.BaseKafkaConsumerConfig
import `in`.ding.user.auth.domain.event.AuthBaseEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class AuthKafkaConsumerConfig(
    private val base: BaseKafkaConsumerConfig
) {

    @Value("\${kafka.consumer.auth.group-id:auth-service-group}")
    private lateinit var groupId: String

    @Bean
    fun authKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, AuthBaseEvent> {
        return base.kafkaListenerContainerFactory(groupId, AuthBaseEvent::class.java)
    }
}
