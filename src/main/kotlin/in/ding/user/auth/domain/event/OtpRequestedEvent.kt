package `in`.ding.user.auth.domain.event

import `in`.ding.common.kafka.EventType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime

data class OtpRequestedEvent(
    val contact: String,
    val nationality: UserNationality,
    val requestId: String,
    override val eventName: String = "otp_requested_event",
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now()
) : AuthEvent
