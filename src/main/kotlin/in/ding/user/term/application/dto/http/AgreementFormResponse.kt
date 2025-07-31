package `in`.ding.user.term.application.dto.http

import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.model.enumerate.TermTitle
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import java.util.*

data class AgreementFormResponse(
    val terms: List<TermDto>
) {
    companion object {
        fun of(terms: List<RequiredTermForm>): AgreementFormResponse {
            return AgreementFormResponse(
                terms.map {
                    TermDto(
                        it.termExKey,
                        it.title,
                        it.content,
                        it.isRequired,
                        it.version,
                        it.country,
                        it.agreementExKey?.let { false } ?: true
                    )
                }
            )
        }
    }
}

data class TermDto(
    val exKey: UUID,
    val title: TermTitle,
    val content: String,
    val isRequired: Boolean,
    val version: String,
    val country: UserNationality?,
    val isAgreed: Boolean
)
