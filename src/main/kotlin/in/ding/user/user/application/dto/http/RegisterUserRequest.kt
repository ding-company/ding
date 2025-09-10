package `in`.ding.user.user.application.dto.http

import java.util.UUID

data class RegisterUserRequest(
    val termsAgreement: List<TermsAgreementRequest>
)

data class TermsAgreementRequest(
    val termExKey: UUID,
    val isAgreed: Boolean,
)
