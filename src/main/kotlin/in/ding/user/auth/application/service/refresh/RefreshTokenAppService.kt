package `in`.ding.user.auth.application.service.refresh

import `in`.ding.common.infra.security.jwt.JwtTokenProvider
import `in`.ding.common.infra.security.jwt.TokenIssuer
import `in`.ding.common.infra.security.jwt.exception.InvalidTokenException
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.user.auth.application.rest.response.RefreshTokenResponse
import `in`.ding.user.auth.domain.model.RefreshToken
import `in`.ding.user.auth.domain.refresh.RefreshTokenValidator
import `in`.ding.user.auth.domain.repository.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenAppService(
    private val tokenIssuer: TokenIssuer,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val refreshTokenValidator: RefreshTokenValidator,
) {

    fun refresh(refreshTokenValue: String): RefreshTokenResponse {
        val payload = jwtTokenProvider.parse(refreshTokenValue)

        require(payload.tokenType != TokenType.REFRESH) {
            "not refresh token"
        }

        val stored = refreshTokenRepository.find(refreshTokenValue)
            ?: throw InvalidTokenException()

        refreshTokenValidator.validate(stored)

        // rotation
        refreshTokenRepository.markUsed(refreshTokenValue)

        val newAccess = tokenIssuer.issueAuthenticationToken(
            subject = payload.subject,
            tokenType = payload.tokenType,
        )

        val newRefresh = tokenIssuer.issueRefreshToken(
            subject = payload.subject,
        )

        refreshTokenRepository.save(
            RefreshToken(
                token = newRefresh.token,
                subject = payload.subject,
                tokenType = TokenType.REFRESH,
                expiresAt = newRefresh.issuedAt.plusSeconds(newRefresh.expiresInSeconds),
                absoluteExpiresAt =
                stored.absoluteExpiresAt, // 최초 값 유지
                isUsed = false,
            )
        )

        return RefreshTokenResponse(newAccess.token, newRefresh.token, newRefresh.expiresInSeconds)
    }
}
