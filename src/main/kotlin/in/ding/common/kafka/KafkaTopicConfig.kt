package `in`.ding.common.kafka

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KafkaTopicsProperties::class)
class KafkaTopicConfig
