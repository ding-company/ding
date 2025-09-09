package `in`.ding.user.term.application.dto.http

import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.domain.model.enumerate.UserType
import `in`.ding.user.user.domain.model.enumerate.UserNationality

data class QueryTermsAgreementFormRequest(
    val appType: AppType,
    val userType: UserType,
    val country: UserNationality
)
