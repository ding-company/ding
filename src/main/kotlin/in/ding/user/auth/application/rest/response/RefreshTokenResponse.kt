package `in`.ding.user.auth.application.rest.response

import `in`.ding.common.infra.security.jwt.model.BaseTokenResponse

data class RefreshTokenResponse(
    val token: BaseTokenResponse
)
