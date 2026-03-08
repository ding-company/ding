package `in`.ding.user.auth.application.rest.response

import `in`.ding.common.infra.security.jwt.model.BaseTokenResponse

data class OtpIssueResponse(
    val token: BaseTokenResponse
)
