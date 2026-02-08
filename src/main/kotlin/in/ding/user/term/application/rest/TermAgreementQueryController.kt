package `in`.ding.user.term.application.rest

import `in`.ding.common.infra.security.principal.AuthPrincipal
import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.application.dto.http.QueryTermsAgreementFormRequest
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.application.service.TermQueryService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/agreements")
class TermAgreementQueryController(
    private val agreementQueryService: TermQueryService
) {

    // TODO auth
    @GetMapping("/form")
    fun getForm(
        @AuthenticationPrincipal authPrincipal: AuthPrincipal,
        @ModelAttribute request: QueryTermsAgreementFormRequest
    ): AgreementFormResponse {
        val query = TermAgreementFormQuery.of(request, authPrincipal.subject)
        return agreementQueryService.getUnagreedRequiredTerms(query)
    }
}
