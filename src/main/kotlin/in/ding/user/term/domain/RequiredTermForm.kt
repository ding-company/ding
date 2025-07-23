package `in`.ding.user.term.domain

import `in`.ding.user.term.domain.model.enumerate.TermTitle
import java.util.UUID

data class RequiredTermForm(
    val exKey: UUID,
    val title: TermTitle,
    val content: String,
    val isRequired: Boolean
)
