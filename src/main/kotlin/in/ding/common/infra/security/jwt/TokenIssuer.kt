package `in`.ding.common.infra.security.jwt

import `in`.ding.common.infra.security.jwt.model.IssuedToken
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.common.toDate
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Component
class TokenIssuer(
    private val jwtProperties: JwtProperties,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun issueAuthenticationToken(subject: UUID, tokenType: TokenType): IssuedToken {
        val issuedAt = now()
        val expiresInSeconds = validityOf(tokenType)
        val expiresAt = issuedAt.plus(expiresInSeconds)
        val token = issueJwt(
            subject = subject,
            tokenType = tokenType,
            issuedAt = issuedAt,
            expiresAt = expiresAt
        )
        return IssuedToken(
            token = token,
            issuedAt = issuedAt,
            tokenType = tokenType,
            expiresInSeconds = expiresInSeconds.toSeconds(),
        )
    }

    fun issueRefreshToken(
        subject: UUID,
        absoluteExpiresAt: LocalDateTime? = null
    ): IssuedToken {
        val issuedAt = now()
        val tokenType = TokenType.REFRESH
        val resolvedAbsoluteExpiresAt = absoluteExpiresAt ?: issuedAt.plus(jwtProperties.refreshTokenAbsoluteValidity)

        val expiresInDuration = minOf(
            validityOf(tokenType),
            Duration.between(issuedAt, resolvedAbsoluteExpiresAt)
        )
        val expiresAt = issuedAt.plus(expiresInDuration)
        val token = issueJwt(
            subject = subject,
            issuedAt = issuedAt,
            tokenType = tokenType,
            expiresAt = expiresAt
        )
        return IssuedToken(
            token = token,
            tokenType = tokenType,
            issuedAt = issuedAt,
            expiresInSeconds = expiresInDuration.toSeconds(),
            absoluteExpiresAt = resolvedAbsoluteExpiresAt
        )
    }
    private fun issueJwt(
        subject: UUID,
        tokenType: TokenType,
        issuedAt: LocalDateTime,
        expiresAt: LocalDateTime,
    ): String =
        Jwts.builder()
            .setSubject(subject.toString())
            .claim("tokenType", tokenType.name)
            .setIssuedAt(issuedAt.toDate())
            .setExpiration(expiresAt.toDate())
            .signWith(key)
            .compact()

    private fun validityOf(type: TokenType): Duration =
        when (type) {
            TokenType.OTP -> jwtProperties.otpTokenValidity
            TokenType.PRE_AUTH -> jwtProperties.preAuthTokenValidity
            TokenType.ACCESS -> jwtProperties.accessTokenValidity
            TokenType.REFRESH -> jwtProperties.refreshTokenValidity
        }

    private fun now(): LocalDateTime =
        LocalDateTime.now(clock)
}
