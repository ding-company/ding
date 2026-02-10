package `in`.ding.user.auth.application.rest.response

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String?,
    val expiresInSeconds: Long,
)
