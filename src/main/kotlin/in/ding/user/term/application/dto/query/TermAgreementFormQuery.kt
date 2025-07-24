package `in`.ding.user.term.application.dto.query

import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.domain.model.enumerate.UserType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.util.*

data class TermAgreementFormQuery(
    val userExKey: UUID,
    val appType: AppType,
    val userType: UserType,
    val country: UserNationality
)
