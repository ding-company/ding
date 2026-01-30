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
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
@Repository
class TermQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : TermQueryRepository {

    override fun findRequiredTerms(query: TermAgreementFormQuery): List<RequiredTermForm> {
        val termCondition = QTermConditionEntity.termConditionEntity
        val term = QTermEntity.termEntity
        val agreement = QTermAgreementEntity.termAgreementEntity
        val now = LocalDateTime.now()

        return queryFactory
            .select(
                Projections.constructor(
                    RequiredTermForm::class.java,
                    term.exKey,
                    term.title,
                    term.content,
                    term.version,
                    termCondition.country,
                    agreement.exKey,
                    agreement.expiredAt,
                    agreement.status
                )
            )
            .from(termCondition)
            .join(termCondition.term, term)
            .leftJoin(agreement)
            .on(
                agreement.userExKey.eq(query.userExKey)
                    .and(agreement.term.id.eq(term.id))
            )
            .where(
                termCondition.userType.eq(query.userType),
                termCondition.appType.`in`(query.appType, AppType.ALL),
                termCondition.country.isNull
                    .or(termCondition.country.eq(query.country)),
                termCondition.isRequired.isTrue,
                term.effectiveFrom.loe(now),
                term.effectiveTo.isNull.or(term.effectiveTo.goe(now))
            )
            .fetch()
    }
}
