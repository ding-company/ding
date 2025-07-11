package `in`.ding.user.auth.application.dto.response

import `in`.ding.user.auth.domain.service.dto.TokenSet

data class OtpVerifyResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun of(tokenSet: TokenSet): OtpVerifyResponse {
            return OtpVerifyResponse(accessToken = tokenSet.accessToken, refreshToken = tokenSet.refreshToken)
        }
    }
}
