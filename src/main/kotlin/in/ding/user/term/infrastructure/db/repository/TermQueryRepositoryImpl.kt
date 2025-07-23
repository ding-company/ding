package `in`.ding.user.term.infrastructure.db.repository

import `in`.ding.user.term.domain.RequiredTermForm
import `in`.ding.user.term.domain.TermQueryRepository
import jakarta.persistence.EntityManager
import java.util.UUID

class TermQueryRepositoryImpl(
    private val em: EntityManager
) : TermQueryRepository {
    override fun findRequiredTermsNotAgreedBy(userExKey: UUID): List<RequiredTermForm> {
        val query = """
            SELECT new in.ding.user.term.domain.RequiredTermForm(
                t.exKey, t.title, t.content, t.isRequired
            )
            FROM TermEntity t
            WHERE t.isRequired = true
            AND t.id NOT IN (
                SELECT a.exKey
                FROM TermAgreementEntity a
                WHERE a.userExKey = :userId
            )
        """.trimIndent()

        return em.createQuery(query, RequiredTermForm::class.java)
            .setParameter("userExKey", userExKey)
            .resultList
    }
}
