package `in`.ding.common.kafka
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.FixedBackOff

@Configuration
open class BaseKafkaConsumerConfig(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private lateinit var offsetReset: String

    @Value("\${spring.kafka.consumer.key-deserializer}")
    private lateinit var keyDeserializer: String

    @Value("\${spring.kafka.consumer.value-deserializer}")
    private lateinit var valueDeserializer: String

    @Value("\${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private lateinit var trustedPackages: String

    companion object {
        private const val RETRY_INTERVAL_MS = 5000L
        private const val RETRY_COUNT = 5L
    }
    fun <T : Any> consumerFactory(groupId: String, valueType: Class<T>): ConsumerFactory<String, T> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to offsetReset,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to keyDeserializer,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to valueDeserializer,
            JsonDeserializer.TRUSTED_PACKAGES to trustedPackages,
            JsonDeserializer.VALUE_DEFAULT_TYPE to valueType.name
        )
        return DefaultKafkaConsumerFactory(config)
    }
    fun <T : Any> kafkaListenerContainerFactory(
        groupId: String,
        valueType: Class<T>
    ): ConcurrentKafkaListenerContainerFactory<String, T> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, T>()
        factory.consumerFactory = consumerFactory(groupId, valueType)
        factory.setCommonErrorHandler(
            DefaultErrorHandler(
                DeadLetterPublishingRecoverer(kafkaTemplate) { record, _ ->
                    TopicPartition(record.topic() + ".DLT", record.partition())
                },
                FixedBackOff(RETRY_INTERVAL_MS, RETRY_COUNT)
            )
        )
        return factory
    }
}
