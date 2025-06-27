package `in`.ding.user.auth.domain.event

import `in`.ding.common.kafka.EventType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime

data class OtpIssueEvent(
    val contact: String,
    val nationality: UserNationality,
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now()
) : AuthEvent
