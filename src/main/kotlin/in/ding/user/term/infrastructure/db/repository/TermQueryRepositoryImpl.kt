package `in`.ding.user.term.infrastructure.db.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.TermQueryRepository
import `in`.ding.user.term.infrastructure.db.table.QTermAgreementEntity
import `in`.ding.user.term.infrastructure.db.table.QTermConditionEntity
import `in`.ding.user.term.infrastructure.db.table.QTermEntity

class TermQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : TermQueryRepository {
    override fun findRequiredTermsNotAgreedBy(query: TermAgreementFormQuery): List<RequiredTermForm> {
        val term = QTermEntity.termEntity
        val agreement = QTermAgreementEntity.termAgreementEntity
        val termCondition = QTermConditionEntity.termConditionEntity

        return queryFactory
            .select(
                RequiredTermForm(
                    term.exKey,
                    term.title,
                    term.content,
                    term.required,
                    term.version,
                    term.country,
                    agreement.exKey.isNotNull
                )
            )
            .from(termCondition)
            .leftJoin(agreement)
            .on(
                agreement.termExKey.eq(term.exKey)
                    .and(agreement.userExKey.eq(query.userExKey))
            )
            .where(
                term.userType.eq(query.userType),
                term.appType.eq(query.appType),
                term.country.isNull.or(term.country.eq(query.country)) // 공통 + 해당 국가
            )
            .orderBy(term.required.desc(), term.createdAt.asc())
            .fetch()
    }
}
