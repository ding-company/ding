package `in`.ding.common.kafka

import java.time.LocalDateTime

interface DomainEvent {
    val eventType: EventType
    val occurredAt: LocalDateTime
}
