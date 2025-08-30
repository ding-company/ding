package `in`.ding.customer.domain.event

import `in`.ding.common.kafka.EventType
import java.time.LocalDateTime
import java.util.UUID

data class CustomerCreatedEvent(
    val exKey: UUID,
    val userExKey: UUID,
    val phoneNumber: String?,
    override val eventName: String = "customer_created_event",
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now()
) : CustomerEvent
