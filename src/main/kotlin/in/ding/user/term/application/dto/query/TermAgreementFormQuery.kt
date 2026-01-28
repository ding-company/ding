package `in`.ding.user.term.application.dto.query

import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.application.dto.http.QueryTermsAgreementFormRequest
import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.domain.model.enumerate.UserType
import java.util.*

data class TermAgreementFormQuery(
    val userExKey: UUID,
    val appType: AppType,
    val userType: UserType,
    val country: UserNationality
) {
    companion object {
        fun of(request: QueryTermsAgreementFormRequest, userExKey: UUID): TermAgreementFormQuery {
            return TermAgreementFormQuery(userExKey, request.appType, request.userType, request.country)
        }
    }
}
