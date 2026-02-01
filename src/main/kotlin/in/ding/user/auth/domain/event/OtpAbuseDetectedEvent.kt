package `in`.ding.user.auth.domain.event

import `in`.ding.common.event.EventType

data class OtpAbuseDetectedEvent(
    val contact: String,
    override val eventType: EventType = EventType.SYSTEM,
    override val eventName: String = "otp_abuse_detected_event",
) : AuthBaseEvent(eventName, eventType)
