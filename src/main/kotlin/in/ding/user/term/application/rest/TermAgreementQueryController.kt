package `in`.ding.user.term.application.rest

import `in`.ding.common.auth.AuthUser
import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.application.service.AgreementQueryService
import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.domain.model.enumerate.UserType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/agreements")
class TermAgreementQueryController(
    private val agreementQueryService: AgreementQueryService
) {

    @GetMapping("/form")
    fun getForm(
        @AuthenticationPrincipal user: AuthUser,
        @RequestParam appType: AppType,
        @RequestParam userType: UserType,
        @RequestParam country: UserNationality
    ): AgreementFormResponse {
        val query = TermAgreementFormQuery(userExKey = user.exKey, appType = appType, userType = userType, country)
        return agreementQueryService.getUnagreedRequiredTerms(query)
    }
}
