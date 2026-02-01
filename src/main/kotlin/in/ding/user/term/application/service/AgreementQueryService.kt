package `in`.ding.user.term.application.service

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.common.log.errorJson
import `in`.ding.common.log.logger
import `in`.ding.user.term.application.dto.http.AgreementFormResponse
import `in`.ding.user.term.application.dto.http.TermDto
import `in`.ding.user.term.application.dto.query.TermAgreementFormQuery
import `in`.ding.user.term.application.event.AgreementBecameOrphanedEvent
import `in`.ding.user.term.domain.TermQueryRepository
import `in`.ding.user.term.domain.exception.AgreementTargetUnavailable
import `in`.ding.user.term.domain.model.enumerate.TermAgreementStatus
import `in`.ding.user.term.domain.policy.AgreementStatusPolicy
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AgreementQueryService(
    private val termQueryRepository: TermQueryRepository,
    private val publisher: EventPublisher,
) {
    private val logger = logger<AgreementQueryService>()
    fun getUnagreedRequiredTerms(query: TermAgreementFormQuery): AgreementFormResponse {
        val now = LocalDateTime.now()
        val requiredTerms = termQueryRepository.findRequiredTerms(query)

        requiredTerms.forEach {
            if (it.agreementStatus != null && it.agreementStatus == TermAgreementStatus.DELETED) {
                logger.errorJson(
                    mapOf(
                        "msg" to
                            "Invariant violation: DELETED agreement accessed",
                        "detail" to mapOf(
                            "userExKey" to query.userExKey,
                            "agreementExKey" to it.agreementExKey
                        )
                    )
                )
                publisher.publish(
                    AgreementBecameOrphanedEvent(
                        it.agreementExKey,
                        query.userExKey,
                    )
                )
                throw AgreementTargetUnavailable()
            }
        }

        val terms = requiredTerms.map {
            TermDto(
                exKey = it.termExKey,
                title = it.title,
                content = it.content,
                version = it.version,
                country = it.country,
                isAgreed = AgreementStatusPolicy.isAgreed(
                    it.agreementStatus,
                    it.expiredAt,
                    now
                )
            )
        }

        return AgreementFormResponse(terms)
    }
}
