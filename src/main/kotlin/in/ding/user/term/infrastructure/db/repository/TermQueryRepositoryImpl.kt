package `in`.ding.user.term.infrastructure.db.repository

import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.TermQueryRepository
import jakarta.persistence.EntityManager
import java.time.LocalDateTime

class TermQueryRepositoryImpl(
    private val em: EntityManager
) : TermQueryRepository {
    override fun findRequiredTermsNotAgreedBy(query: TermAgreementFormQuery): List<RequiredTermForm> {
        val now = LocalDateTime.now()

        // 2. 조건에 맞는 Term + TermCondition + TermAgreement 조인해서 가져오기
        val queryString = em.createQuery(
            """
            SELECT NEW in.ding.user.term.domain.RequiredTermForm(
                t.exKey,
                t.title,
                t.content,
                t.version,
                tc.isRequired,
                t.defaultAgreementValidityPeriod
            )
            FROM TermEntity t
            JOIN TermConditionEntity tc ON tc.term.id = t.id
            LEFT JOIN TermAgreementEntity ta ON ta.term.id = t.id AND ta.userExKey = :userExKey
            WHERE tc.appType = :appType
              AND tc.userType = :userType
              AND (tc.country IS NULL OR tc.country = :countryCode)
            """.trimIndent(),
            RequiredTermForm::class.java
        )
            .setParameter("appType", query.appType)
            .setParameter("userType", query.userType)
            .setParameter("countryCode", query.country)
            .setParameter("now", now)
            .setParameter("userExKey", query.userExKey)

        return queryString.resultList
    }
}
