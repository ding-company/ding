package `in`.ding.user.term.application.event

import `in`.ding.common.event.EventType
import java.util.UUID

data class AgreementBecameOrphanedEvent(
    val agreementExKey: UUID?,
    val userExKey: UUID,
    override val eventType: EventType = EventType.SYSTEM,
    override val eventName: String = "agreement_became_orphaned_event"
) : TermBaseEvent(eventName, eventType)
