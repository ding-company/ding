package `in`.ding.user.term.infrastructure.db.table

import `in`.ding.user.term.domain.model.enumerate.TermTitle

data class ContentSnapshot(
    val title: TermTitle,
    val body: String,
    val version: Int
)
