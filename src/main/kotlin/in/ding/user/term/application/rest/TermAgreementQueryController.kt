package `in`.ding.user.term.application.rest

import `in`.ding.common.auth.AuthUser
import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.application.service.AgreementQueryService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/agreements")
class TermAgreementQueryController(
    private val agreementQueryService: AgreementQueryService
) {

    @GetMapping("/form")
    fun getForm(@AuthenticationPrincipal user: AuthUser): AgreementFormResponse {
        return agreementQueryService.getUnagreedRequiredTerms(user.exKey)
    }
}
