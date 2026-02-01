package `in`.ding.user.auth.domain.event

import `in`.ding.common.domain.event.EventType
import `in`.ding.user.domain.enumerate.UserNationality

data class OtpVerifiedEvent(
    val contact: String,
    val nationality: UserNationality,
    override val eventName: String = "otp_verified_event",
    override val eventType: EventType = EventType.CREATED,
) : AuthBaseEvent(eventName, eventType)
