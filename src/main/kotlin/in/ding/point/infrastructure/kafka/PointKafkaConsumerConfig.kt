package `in`.ding.point.infrastructure.kafka

import `in`.ding.common.infra.kafka.BaseKafkaConsumerConfig
import `in`.ding.customer.domain.event.CustomerCreatedEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory

@Configuration
class PointKafkaConsumerConfig(
    private val base: BaseKafkaConsumerConfig
) {

    @Value("\${kafka.consumer.user.group-id:point-service-group}")
    private lateinit var groupId: String

    @Bean
    fun pointKafkaListenerContainerFactory():
        ConcurrentKafkaListenerContainerFactory<String, CustomerCreatedEvent> {
        return base.kafkaListenerContainerFactory(groupId, CustomerCreatedEvent::class.java)
    }
}
