package `in`.ding.common.event

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "event.topics")
data class EventTopicsProperties(
    val auth: String,
    val user: String,
    val term: String,
    val payment: String,
    val customer: String,
)
