package `in`.ding.user.auth.domain.model

import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.model.vo.OtpCode
import java.time.Duration
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
        const val MAX_TRY_COUNT = 5
        const val OTP_TTL_MIN = 5L
        const val RETRY_TRACK_TTL_MIN = 10L
        const val BLOCK_DURATION_MIN = 60L
        fun create(contact: String, code: OtpCode): OtpSession {
            val issuedAt = LocalDateTime.now()
            val expiredAt = issuedAt.plusMinutes(OTP_TTL_MIN)
            return OtpSession(contact, code, issuedAt, expiredAt)
        }
        fun getOtpTtl(): Duration = Duration.ofMinutes(OTP_TTL_MIN)
        fun getRetryTrackTtl(): Duration = Duration.ofMinutes(RETRY_TRACK_TTL_MIN)
        fun getBlockDuration(): Duration = Duration.ofMinutes(BLOCK_DURATION_MIN)
    }

    fun verify(otpCode: String): OtpSession {
        if (this.code.value != otpCode) throw InvalidOtpException()
        if (isExpired()) throw ExpiredOtpException()
        return this.copy(verified = true)
    }

    private fun isExpired(): Boolean =
        issuedAt.plusMinutes(OTP_TTL_MIN).isBefore(LocalDateTime.now())

    fun incrementTry(): OtpSession = this.copy(tryCount = tryCount + 1)

    fun isLockable(): Boolean = tryCount >= MAX_TRY_COUNT
}
