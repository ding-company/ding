package `in`.ding.user.auth.domain.event

import `in`.ding.common.kafka.EventType

data class OtpAbuseDetectedEvent(
    val contact: String,
    override val eventName: String = "otp_abuse_detected_event",
    override val eventType: EventType = EventType.CREATED,
) : AuthEvent(eventName, eventType)
