package `in`.ding.user.term.application.dto.http

import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.domain.model.enumerate.UserType

data class QueryTermsAgreementFormRequest(
    val appType: AppType,
    val userType: UserType,
    val country: UserNationality
)
