package `in`.ding.user.user.application.dto.http

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RegisterUserRequest(
    @JsonProperty("terms_agreement")
    val termsAgreement: List<TermsAgreementRequest>
)

data class TermsAgreementRequest(
    val termExKey: UUID,
    val isAgreed: Boolean,
)
