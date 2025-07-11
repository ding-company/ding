package `in`.ding.user.term.domain.model

import `in`.ding.user.term.domain.model.enumerate.TermAgreementStatus
import `in`.ding.user.term.domain.model.id.TermAgreementId
import `in`.ding.user.term.domain.model.id.TermId
import java.time.LocalDateTime
import java.util.UUID

data class TermAgreement(
    val id: TermAgreementId,
    val userExKey: UUID,
    val termId: TermId,
    val agreedAt: LocalDateTime,
    val expiresAt: LocalDateTime? = null,
    val status: TermAgreementStatus,
    val withdrawnAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
    val version: Long = 0
) {
    fun withdraw(now: LocalDateTime = LocalDateTime.now()): TermAgreement {
        return copy(status = TermAgreementStatus.WITHDRAWN, withdrawnAt = now)
    }

    fun expire(): TermAgreement {
        return copy(status = TermAgreementStatus.EXPIRED)
    }

    fun delete(now: LocalDateTime = LocalDateTime.now()): TermAgreement {
        return copy(status = TermAgreementStatus.DELETED, deletedAt = now)
    }

    fun isActive(): Boolean = status == TermAgreementStatus.AGREED
}
