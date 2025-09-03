package `in`.ding.customer.domain.event

import `in`.ding.common.kafka.EventType
import java.util.UUID

data class CustomerCreatedEvent(
    val exKey: UUID,
    val userExKey: UUID,
    val phoneNumber: String?,
    override val eventName: String = "customer_created_event",
    override val eventType: EventType = EventType.CREATED,
) : CustomerEvent(eventName, eventType)
