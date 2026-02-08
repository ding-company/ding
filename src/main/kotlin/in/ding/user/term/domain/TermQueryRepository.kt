package `in`.ding.user.term.domain

import `in`.ding.common.infra.jpa.ID
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import `in`.ding.user.term.domain.model.enumerate.ServiceChannel
import `in`.ding.user.term.infrastructure.db.table.TermEntity
import java.util.UUID

interface TermQueryRepository {
    fun findRequiredTerms(query: TermAgreementFormQuery): List<RequiredTermForm>
    fun findRequiredTerms(
        serviceChannel: ServiceChannel?,
        principalType: PrincipalType,
        country: UserNationality
    ): List<TermEntity>
    fun countAgreedByTermIdsAndUserExKey(termIds: List<ID>, userExKey: UUID): Long
}
