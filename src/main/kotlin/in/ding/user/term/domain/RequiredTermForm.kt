package `in`.ding.user.term.domain

import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.TermAgreementStatus
import `in`.ding.user.term.domain.model.enumerate.TermTitle
import java.time.LocalDateTime
import java.util.UUID

data class RequiredTermForm(
    val termExKey: UUID,
    val title: TermTitle,
    val content: String,
    val version: String,
    val country: UserNationality?,
    val agreementExKey: UUID?,
    val expiredAt: LocalDateTime?,
    val agreementStatus: TermAgreementStatus?
)
