package `in`.ding.user.auth.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import `in`.ding.common.domain.EventRecorder
import `in`.ding.user.auth.domain.event.AuthEvent
import `in`.ding.user.auth.domain.event.OtpIssuedEvent
import `in`.ding.user.auth.domain.event.OtpVerifiedEvent
import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.user.domain.model.enumerate.ContactType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.time.Duration
import java.time.LocalDateTime
data class OtpSession(
    val contact: String,
    val code: OtpCode,
    val issuedAt: LocalDateTime,
    val expiredAt: LocalDateTime,
    val tryCount: Int = 0,
    val verified: Boolean = false,
    @JsonIgnore private val events: EventRecorder<AuthEvent> = EventRecorder()
) {
    @JsonIgnore
    fun drainEvents(): List<AuthEvent> = events.drain()
//    val domainEvents: List<AuthEvent> get() = events.toList()

    companion object {
        const val MAX_TRY_COUNT = 5
        const val OTP_TTL_MIN = 5L
        const val RETRY_TRACK_TTL_MIN = 10L
        const val BLOCK_DURATION_MIN = 60L

        fun issue(
            contact: String,
            code: OtpCode,
            contactType: ContactType
        ): OtpSession {
            val now = LocalDateTime.now()
            val session = OtpSession(
                contact = contact,
                code = code,
                issuedAt = now,
                expiredAt = now.plusMinutes(OTP_TTL_MIN)
            )

            session.recordEvent(
                OtpIssuedEvent.of(
                    otp = session,
                    contactType = contactType
                )
            )

            return session
        }

        fun getOtpTtl(): Duration = Duration.ofMinutes(OTP_TTL_MIN)
        fun getRetryTrackTtl(): Duration = Duration.ofMinutes(RETRY_TRACK_TTL_MIN)
        fun getBlockDuration(): Duration = Duration.ofMinutes(BLOCK_DURATION_MIN)

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

    private fun recordEvent(event: AuthEvent) {
        events.add(event)
    }

    fun clearEvents() {
        events.clear()
    }
}
