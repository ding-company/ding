package `in`.ding.common.kafka
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.FixedBackOff

@Configuration
open class BaseKafkaConsumerConfig(
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private lateinit var offsetReset: String

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
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS to StringDeserializer::class.java,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS to JsonDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to trustedPackages,
            JsonDeserializer.VALUE_DEFAULT_TYPE to valueType.name
        )

        val jsonDeserializer = JsonDeserializer(valueType, objectMapper)
        return DefaultKafkaConsumerFactory(config, StringDeserializer(), jsonDeserializer)
    }
    fun <T : Any> kafkaListenerContainerFactory(
        groupId: String,
        valueType: Class<T>
    ): ConcurrentKafkaListenerContainerFactory<String, T> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, T>()
        factory.consumerFactory = consumerFactory(groupId, valueType)

        val recoverer = DeadLetterPublishingRecoverer(kafkaTemplate) { record, _ ->
            TopicPartition(record.topic() + ".DLT", record.partition())
        }

        val errorHandler = DefaultErrorHandler(
            recoverer,
            FixedBackOff(RETRY_INTERVAL_MS, RETRY_COUNT)
        )

        factory.setCommonErrorHandler(errorHandler)
        return factory
    }
}
