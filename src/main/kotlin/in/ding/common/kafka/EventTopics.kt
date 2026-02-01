package `in`.ding.common.kafka

import org.springframework.stereotype.Component

@Component
class EventTopics(
    private val props: KafkaTopicsProperties
) {
    fun domain(context: String): String =
        props.domain[context]
            ?: error("Domain topic not found: $context")

    fun application(context: String): String =
        props.application[context]
            ?: error("Application topic not found: $context")

    fun system(name: String): String =
        props.system[name]
            ?: error("System topic not found: $name")
}
