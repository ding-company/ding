package `in`.ding.user.term.application.service

import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.TermQueryRepository
import org.springframework.stereotype.Service

@Service
class AgreementQueryService(
    private val termQueryRepository: TermQueryRepository,
) {

    fun getUnagreedRequiredTerms(query: TermAgreementFormQuery): AgreementFormResponse {
        return AgreementFormResponse.of(termQueryRepository.findRequiredTermsNotAgreedBy(query))
    }
}
