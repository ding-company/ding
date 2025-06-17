package `in`.ding.customer.domain.event

import `in`.ding.common.event.EventType
import java.time.LocalDateTime
import java.util.UUID

data class CustomerCreatedEvent(
    val exKey: UUID,
    val userExKey: UUID,
    val phoneNumber: String?,
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now()
) : CustomerEvent
