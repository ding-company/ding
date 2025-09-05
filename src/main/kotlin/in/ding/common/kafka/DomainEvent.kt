package `in`.ding.common.kafka

import java.time.LocalDateTime
import java.util.*

interface DomainEvent {
    val eventName: String
    val eventType: EventType
    val eventId: UUID
    val occurredAt: LocalDateTime
    val version: EventVersion
}
