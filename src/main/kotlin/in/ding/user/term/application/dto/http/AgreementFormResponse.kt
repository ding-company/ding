package `in`.ding.user.term.application.dto.http

import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.model.enumerate.TermTitle
import java.util.*

data class AgreementFormResponse(
    val terms: List<TermDto>
) {
    companion object {
        fun of(terms: List<RequiredTermForm>): AgreementFormResponse {
            return AgreementFormResponse(
                terms.map { TermDto(it.exKey, it.title, it.content) }
            )
        }
    }
}

data class TermDto(
    val exKey: UUID,
    val title: TermTitle,
    val content: String
)
