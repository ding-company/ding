package `in`.ding.user.term.infrastructure.db.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import `in`.ding.common.infra.jpa.ID
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.TermQueryRepository
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import `in`.ding.user.term.domain.model.enumerate.ServiceChannel
import `in`.ding.user.term.infrastructure.db.table.QTermAgreementEntity
import `in`.ding.user.term.infrastructure.db.table.QTermConditionEntity
import `in`.ding.user.term.infrastructure.db.table.QTermEntity
import `in`.ding.user.term.infrastructure.db.table.TermEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

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
            .join(term.termCondition, termCondition)
            .leftJoin(agreement)
            .on(
                agreement.userExKey.eq(query.userExKey)
                    .and(agreement.term.id.eq(term.id))
            )
            .where(
                termCondition.principalType.eq(query.principalType),
                termCondition.serviceChannel.`in`(query.serviceChannel, ServiceChannel.COMMON),
                termCondition.country.isNull
                    .or(termCondition.country.eq(query.country)),
                termCondition.isRequired.isTrue,
                term.effectiveFrom.loe(now),
                term.effectiveTo.isNull.or(term.effectiveTo.goe(now))
            )
            .fetch()
    }

    override fun findRequiredTerms(
        serviceChannel: ServiceChannel?,
        principalType: PrincipalType,
        country: UserNationality
    ): List<TermEntity> {
        val termCondition = QTermConditionEntity.termConditionEntity
        val term = QTermEntity.termEntity
        val now = LocalDateTime.now()
        return queryFactory.select(term).from(termCondition).join(term.termCondition, termCondition)
            .where(
                termCondition.principalType.eq(principalType),
                serviceChannelCondition(serviceChannel),
                termCondition.country.isNull
                    .or(termCondition.country.eq(country)),
                termCondition.isRequired.isTrue,
                term.effectiveFrom.loe(now),
                term.effectiveTo.isNull.or(term.effectiveTo.goe(now))
            ).fetch()
    }
    override fun countAgreedByTermIdsAndUserExKey(termIds: List<ID>, userExKey: UUID): Long {
        val agreement = QTermAgreementEntity.termAgreementEntity

        return queryFactory.select(agreement).from(agreement).where(
            agreement.term.id.`in`(termIds),
            agreement.userExKey.eq(userExKey)
        ).fetchCount()
    }
    private fun serviceChannelCondition(
        serviceChannel: ServiceChannel?
    ): BooleanExpression {
        val tc = QTermConditionEntity.termConditionEntity

        return if (serviceChannel == null) {
            tc.serviceChannel.eq(ServiceChannel.COMMON)
        } else {
            tc.serviceChannel.`in`(serviceChannel, ServiceChannel.COMMON)
        }
    }
}
