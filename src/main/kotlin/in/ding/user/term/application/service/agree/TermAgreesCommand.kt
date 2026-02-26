package `in`.ding.user.term.application.service.agree

import java.util.*

data class TermAgreesCommand(
    val verifiedIdentityExKey: UUID,
    val termExKeys: List<UUID>,
)
