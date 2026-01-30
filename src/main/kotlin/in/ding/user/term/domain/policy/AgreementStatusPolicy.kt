package `in`.ding.user.term.domain.policy

import `in`.ding.user.term.domain.model.enumerate.TermAgreementStatus
import java.time.LocalDateTime

object AgreementStatusPolicy {

    fun isAgreed(
        status: TermAgreementStatus?,
        expiredAt: LocalDateTime?,
        now: LocalDateTime
    ): Boolean {
        return when (status) {
            TermAgreementStatus.AGREED ->
                expiredAt == null || expiredAt.isAfter(now)

            TermAgreementStatus.EXPIRED,
            TermAgreementStatus.WITHDRAWN,
            null -> false
            else -> false
        }
    }
}
