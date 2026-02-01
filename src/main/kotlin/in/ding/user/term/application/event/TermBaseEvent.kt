package `in`.ding.user.term.application.event

import `in`.ding.common.event.BaseEvent
import `in`.ding.common.event.EventContext
import `in`.ding.common.event.EventType
import `in`.ding.common.event.EventVersion
import java.time.LocalDateTime
import java.util.*

abstract class TermBaseEvent(
    override val eventName: String,
    override val eventType: EventType,
    override val eventId: UUID = UUID.randomUUID(),
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
    override val version: EventVersion = EventVersion.V1,
    override val eventContext: EventContext = EventContext.TERM
) : BaseEvent
