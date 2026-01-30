package `in`.ding.user.term.domain

import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery

interface TermQueryRepository {
    fun findRequiredTerms(query: TermAgreementFormQuery): List<RequiredTermForm>
}
