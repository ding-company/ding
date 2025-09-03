package `in`.ding.customer.domain.event

import `in`.ding.common.kafka.DomainEvent
import `in`.ding.common.kafka.EventType
import `in`.ding.common.kafka.EventVersion
import java.time.LocalDateTime
import java.util.UUID

abstract class CustomerEvent(

    override val eventName: String,
    override val eventType: EventType,
    override val eventId: UUID = UUID.randomUUID(),
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
    override val version: EventVersion = EventVersion.V1
) : DomainEvent
