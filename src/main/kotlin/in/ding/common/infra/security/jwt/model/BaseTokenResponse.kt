package `in`.ding.common.infra.security.jwt.model

data class BaseTokenResponse(
    val authenticationToken: String,
    val refreshToken: String?,
    val expiresInSeconds: Long,
)
