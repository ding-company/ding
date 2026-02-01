package `in`.ding.customer.domain.event

import `in`.ding.common.domain.event.BaseEvent
import `in`.ding.common.domain.event.EventContext
import `in`.ding.common.domain.event.EventType
import `in`.ding.common.domain.event.EventVersion
import java.time.LocalDateTime
import java.util.UUID

abstract class CustomerEvent(

    override val eventName: String,
    override val eventType: EventType,
    override val eventId: UUID = UUID.randomUUID(),
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
    override val version: EventVersion = EventVersion.V1,
    override val eventContext: EventContext = EventContext.CUSTOMER
) : BaseEvent
