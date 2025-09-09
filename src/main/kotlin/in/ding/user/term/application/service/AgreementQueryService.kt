package `in`.ding.user.term.application.service

import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.application.dto.http.TermDto
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.TermQueryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AgreementQueryService(
    private val termQueryRepository: TermQueryRepository,
) {

    fun getUnagreedRequiredTerms(query: TermAgreementFormQuery): AgreementFormResponse {
        val now = LocalDateTime.now()
        val requiredTerms = termQueryRepository.findRequiredTermsNotAgreedBy(query)

        val terms = requiredTerms.map {
            val isAgreed = if (it.expiredAt < now) {
                false
            } else {
                it.agreementExKey?.let { true } ?: false
            }

            TermDto(
                exKey = it.termExKey,
                title = it.title,
                content = it.content,
                isRequired = it.isRequired,
                version = it.version,
                country = it.country,
                isAgreed = isAgreed
            )
        }
        return AgreementFormResponse(terms)
    }
}
