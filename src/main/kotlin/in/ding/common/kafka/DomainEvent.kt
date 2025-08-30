package `in`.ding.common.kafka

import java.time.LocalDateTime

interface DomainEvent {
    val eventName: String
    val eventType: EventType
    val occurredAt: LocalDateTime
}
