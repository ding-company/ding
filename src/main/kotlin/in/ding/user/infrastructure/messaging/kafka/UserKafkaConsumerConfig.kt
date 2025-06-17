package `in`.ding.user.infrastructure.messaging.kafka

import `in`.ding.common.kafka.BaseKafkaConsumerConfig
import `in`.ding.customer.domain.event.CustomerCreatedEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class UserKafkaConsumerConfig(
    private val base: BaseKafkaConsumerConfig
) {

    @Value("\${kafka.consumer.user.group-id:user-service-group}")
    private lateinit var groupId: String

    @Bean
    fun userKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, CustomerCreatedEvent> {
        return base.kafkaListenerContainerFactory(groupId, CustomerCreatedEvent::class.java)
    }
}
