package `in`.ding.user.infrastructure.messaging.kafka

import `in`.ding.common.event.DomainEvent
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class UserKafkaConsumerConfig {

    @Value("\${spring.kafka.topic.customer}")
    private lateinit var topic: String

    @Bean
    fun userKafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, DomainEvent>
    ): ConcurrentKafkaListenerContainerFactory<String, DomainEvent> {
        return ConcurrentKafkaListenerContainerFactory<String, DomainEvent>().apply {
            this.consumerFactory = consumerFactory
        }
    }

    @Bean
    fun userConsumerFactory(): ConsumerFactory<String, DomainEvent> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "user-service-group",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
        return DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            JsonDeserializer(DomainEvent::class.java)
        )
    }
}
