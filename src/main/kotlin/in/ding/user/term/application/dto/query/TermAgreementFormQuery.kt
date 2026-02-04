package `in`.ding.user.term.application.dto.query

import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.application.dto.http.QueryTermsAgreementFormRequest
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import `in`.ding.user.term.domain.model.enumerate.ServiceChannel
import java.util.*

data class TermAgreementFormQuery(
    val userExKey: UUID,
    val serviceChannel: ServiceChannel,
    val principalType: PrincipalType,
    val country: UserNationality
) {
    companion object {
        fun of(request: QueryTermsAgreementFormRequest, userExKey: UUID): TermAgreementFormQuery {
            return TermAgreementFormQuery(userExKey, request.serviceChannel, request.principalType, request.country)
        }
    }
}
