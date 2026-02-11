package `in`.ding.user.auth.application.rest.response

import `in`.ding.common.infra.security.jwt.model.BaseTokenResponse

data class PostAuthStatusResponse(
    val status: PostAuthStatus,
    val token: BaseTokenResponse
)
