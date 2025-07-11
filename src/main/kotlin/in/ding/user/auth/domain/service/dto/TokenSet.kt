package `in`.ding.user.auth.domain.service.dto

data class TokenSet(
    val accessToken: String,
    val refreshToken: String,
)
