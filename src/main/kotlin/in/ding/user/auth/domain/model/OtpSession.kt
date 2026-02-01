package `in`.ding.user.auth.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import `in`.ding.common.domain.event.EventRecorder
import `in`.ding.user.auth.domain.event.AuthBaseEvent
import `in`.ding.user.auth.domain.event.OtpIssuedEvent
import `in`.ding.user.auth.domain.event.OtpVerifiedEvent
import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.user.domain.model.enumerate.ContactType
import java.time.Duration
import java.time.LocalDateTime
data class OtpSession(
    val contact: String,
    val code: OtpCode,
    val issuedAt: LocalDateTime,
    val expiredAt: LocalDateTime,
    val tryCount: Int = 0,
    val verified: Boolean = false,
    @JsonIgnore private val events: EventRecorder<AuthBaseEvent> = EventRecorder()
) {
    @JsonIgnore
    fun drainEvents(): List<AuthBaseEvent> = events.drain()
    companion object {
        fun lifetime(): DomainLifetime =
            DomainLifetime.OTP_SESSION
        const val MAX_TRY_COUNT = 5

        fun issue(
            contact: String,
            code: OtpCode,
            contactType: ContactType,
            now: LocalDateTime,
            expiredCondition: Duration,
        ): OtpSession {
            val session = OtpSession(
                contact = contact,
                code = code,
                issuedAt = now,
                expiredAt = now.plusMinutes(expiredCondition.toMinutes()),
            )

            session.recordEvent(
                OtpIssuedEvent.of(
                    otp = session,
                    contactType = contactType
                )
            )

            return session
        }

        @Suppress("UnusedPrivateMember", "LongParameterList")
        @JsonCreator
        @JvmStatic
        private fun jsonCreator(
            contact: String,
            code: String,
            issuedAt: LocalDateTime,
            expiredAt: LocalDateTime,
            tryCount: Int,
            verified: Boolean
        ): OtpSession {
            return OtpSession(
                contact = contact,
                code = OtpCode(code),
                issuedAt = issuedAt,
                expiredAt = expiredAt,
                tryCount = tryCount,
                verified = verified
            )
        }
    }
    fun verify(otpCode: String, nationality: UserNationality): OtpSession {
        if (this.code.value != otpCode) throw InvalidOtpException()
        if (isExpired()) throw ExpiredOtpException()

        val verifiedSession = this.copy(verified = true)
        verifiedSession.recordEvent(OtpVerifiedEvent(contact, nationality))
        return verifiedSession
    }
    fun incrementTry(): OtpSession = this.copy(tryCount = tryCount + 1)

    private fun isExpired(): Boolean = expiredAt.isBefore(LocalDateTime.now())

    @JsonIgnore
    fun isLockable(): Boolean = tryCount >= MAX_TRY_COUNT

    private fun recordEvent(event: AuthBaseEvent) {
        events.add(event)
    }

    fun clearEvents() {
        events.clear()
    }
}
