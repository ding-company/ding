package `in`.ding.user.term.domain

import `in`.ding.user.term.domain.model.enumerate.TermTitle
import java.time.Duration
import java.util.UUID

data class RequiredTermForm(
    val exKey: UUID,
    val title: TermTitle,
    val content: String,
    val version: Int,
    val required: Boolean,
    val defaultAgreementValidityPeriod: Duration? = null
)
