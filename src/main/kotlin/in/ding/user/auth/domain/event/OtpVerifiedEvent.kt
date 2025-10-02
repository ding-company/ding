package `in`.ding.user.auth.domain.event

import `in`.ding.common.kafka.EventType
import `in`.ding.user.user.domain.model.enumerate.UserNationality

data class OtpVerifiedEvent(
    val contact: String,
    val nationality: UserNationality,
    override val eventName: String = "otp_verified_event",
    override val eventType: EventType = EventType.CREATED,
) : AuthEvent(eventName, eventType)
