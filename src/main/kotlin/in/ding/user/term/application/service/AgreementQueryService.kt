package `in`.ding.user.term.application.service

import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.domain.TermQueryRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AgreementQueryService(
    private val termQueryRepository: TermQueryRepository,
) {

    fun getUnagreedRequiredTerms(userExKey: UUID): AgreementFormResponse {
        return AgreementFormResponse.of(termQueryRepository.findRequiredTermsNotAgreedBy(userExKey))
    }
}
