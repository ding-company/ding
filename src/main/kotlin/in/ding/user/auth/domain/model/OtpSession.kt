package `in`.ding.user.auth.domain.model

import `in`.ding.common.exception.BadRequestException
import `in`.ding.user.auth.domain.event.OtpIssuedEvent
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.user.domain.model.enumerate.ContactType
import java.time.LocalDateTime

data class OtpSession(
    val contact: String,
    val code: OtpCode,
    val issuedAt: LocalDateTime,
    val expiredAt: LocalDateTime,
    val tryCount: Int = 0,
    val verified: Boolean = false,
) {
    companion object {
        private const val EXPIRED_CONDITION_IN_MIN = 5L
        fun create(contact: String, code: OtpCode): OtpSession {
            val issuedAt = LocalDateTime.now()
            val expiredAt = issuedAt.plusSeconds(EXPIRED_CONDITION_IN_MIN)
            return OtpSession(contact, code, issuedAt = LocalDateTime.now(), expiredAt)
        }
    }
    fun verify(input: String): OtpSession {
        if (this.code.value != input) throw BadRequestException()
        if (isExpired()) throw BadRequestException()
        return this.copy(verified = true)
    }

    fun isExpired(): Boolean = issuedAt.plusMinutes(EXPIRED_CONDITION_IN_MIN).isBefore(LocalDateTime.now())

    fun incrementTry(): OtpSession = this.copy(tryCount = tryCount + 1)

    fun toOtpIssuedEvent(contactType: ContactType): OtpIssuedEvent {
        return OtpIssuedEvent(
            contact = this.contact,
            otpCode = this.code.value,
            contactType = contactType,
            otpExpiredAt = this.expiredAt
        )
    }
}
