package `in`.ding.user.term.infrastructure.db.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.TermQueryRepository
import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.infrastructure.db.table.QTermAgreementEntity
import `in`.ding.user.term.infrastructure.db.table.QTermConditionEntity
import `in`.ding.user.term.infrastructure.db.table.QTermEntity

class TermQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : TermQueryRepository {
    override fun findRequiredTermsNotAgreedBy(query: TermAgreementFormQuery): List<RequiredTermForm> {
        val termCondition = QTermConditionEntity.termConditionEntity
        val term = QTermEntity.termEntity
        val agreement = QTermAgreementEntity.termAgreementEntity

        return queryFactory
            .select(
                Projections.constructor(
                    RequiredTermForm::class.java,
                    term.exKey,
                    term.title,
                    term.content,
                    termCondition.isRequired,
                    term.version,
                    termCondition.country,
                    agreement.exKey
                )
            )
            .from(termCondition)
            .join(termCondition.term, term)
            .leftJoin(agreement)
            .on(
                agreement.userExKey.eq(query.userExKey)
                    .and(agreement.term.id.eq(termCondition.term.id))
            )
            .where(
                termCondition.userType.eq(query.userType),
                termCondition.appType.eq(query.appType).and(termCondition.appType.eq(AppType.ALL)),
                termCondition.country.isNull.or(termCondition.country.eq(query.country)),
                termCondition.isRequired.isTrue,
            )
            .fetch()
    }
}
