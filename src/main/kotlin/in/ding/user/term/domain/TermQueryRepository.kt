package `in`.ding.user.term.domain

import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import org.springframework.stereotype.Repository

@Repository
interface TermQueryRepository {
    fun findRequiredTermsNotAgreedBy(query: TermAgreementFormQuery): List<RequiredTermForm>
}
