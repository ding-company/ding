package `in`.ding.user.auth.domain.event

import `in`.ding.common.domain.event.EventType
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.user.domain.model.enumerate.ContactType
import java.time.LocalDateTime

data class OtpIssuedEvent(
    val contact: String,
    val otpCode: String,
    val contactType: ContactType,
    val otpExpiredAt: LocalDateTime,
    override val eventName: String = "otp_issued_event",
    override val eventType: EventType = EventType.CREATED,
) : AuthBaseEvent(eventName, eventType) {
    companion object {
        fun of(otp: OtpSession, contactType: ContactType): OtpIssuedEvent {
            return OtpIssuedEvent(
                contact = otp.contact,
                otpCode = otp.code.value,
                contactType = contactType,
                otpExpiredAt = otp.expiredAt
            )
        }
    }
}
