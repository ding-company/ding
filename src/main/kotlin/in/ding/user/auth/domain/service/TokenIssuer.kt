package `in`.ding.user.auth.domain.service

import `in`.ding.common.auth.JwtTokenProvider
import `in`.ding.user.auth.domain.service.dto.TokenSet
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TokenIssuer(
    private val tokenProvider: JwtTokenProvider
) {
    fun issueTokens(userExKey: UUID): TokenSet {
        return TokenSet(
            accessToken = tokenProvider.generateAccessToken(userExKey),
            refreshToken = tokenProvider.generateRefreshToken(userExKey)
        )
    }
}
