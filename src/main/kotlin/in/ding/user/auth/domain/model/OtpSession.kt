package `in`.ding.user.auth.domain.model

import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.time.LocalDateTime

data class OtpSession(
    val contact: String,
    val nationality: UserNationality,
    val code: OtpCode,
    val issuedAt: LocalDateTime,
    val attemptCount: Int = 0,
    val maxAttempts: Int = 5,
)
