package `in`.ding.user.term.application.dto.http

import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import `in`.ding.user.term.domain.model.enumerate.ServiceChannel

data class QueryTermsAgreementFormRequest(
    val serviceChannel: ServiceChannel,
    val principalType: PrincipalType,
    val country: UserNationality
)
