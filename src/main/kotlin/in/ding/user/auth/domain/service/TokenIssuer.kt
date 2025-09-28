package `in`.ding.user.auth.domain.service

import `in`.ding.common.auth.JwtTokenProvider
import `in`.ding.user.auth.domain.service.dto.TokenSet
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TokenIssuer(
    private val tokenProvider: JwtTokenProvider
) {
    fun issueTokens(userExKey: UUID, status: UserStatus): TokenSet {
        return TokenSet(
            accessToken = tokenProvider.generateAccessToken(userExKey, status),
            refreshToken = tokenProvider.generateRefreshToken(userExKey, status)
        )
    }
}
