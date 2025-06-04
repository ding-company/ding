package `in`.ding.common.event

import java.time.LocalDateTime

interface DomainEvent {
    val eventType: EventType
    val occurredAt: LocalDateTime
}
