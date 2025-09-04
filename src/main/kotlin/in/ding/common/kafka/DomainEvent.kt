package `in`.ding.common.kafka

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.util.UUID

// TODO application.yaml 안먹히는 이유 알아내야함
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
interface DomainEvent {
    val eventName: String
    val eventType: EventType
    val eventId: UUID
    val occurredAt: LocalDateTime
    val version: EventVersion
}
