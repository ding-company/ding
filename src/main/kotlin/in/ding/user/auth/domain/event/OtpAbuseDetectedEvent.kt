package `in`.ding.user.auth.domain.event

import `in`.ding.common.kafka.EventType
import java.time.LocalDateTime

data class OtpAbuseDetectedEvent(
    val contact: String,
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now()
) : AuthEvent
