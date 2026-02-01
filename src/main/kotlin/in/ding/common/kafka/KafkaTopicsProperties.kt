package `in`.ding.common.kafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("kafka.topics")
data class KafkaTopicsProperties(
    val domain: Map<String, String>,
    val application: Map<String, String>,
    val system: Map<String, String>
)
