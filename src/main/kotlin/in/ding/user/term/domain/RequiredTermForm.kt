package `in`.ding.user.term.domain

import `in`.ding.user.term.domain.model.enumerate.TermTitle
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.util.UUID

data class RequiredTermForm(
    val termExKey: UUID,
    val title: TermTitle,
    val content: String,
    val isRequired: Boolean,
    val version: String,
    val country: UserNationality?,
    val isAgreed: Boolean
)
