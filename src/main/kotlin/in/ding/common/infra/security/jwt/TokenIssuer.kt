package `in`.ding.common.infra.security.jwt

import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.common.toDate
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
@Component
class TokenIssuer(
    private val jwtProperties: JwtProperties
) {
    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun issue(
        subject: UUID,
        tokenType: TokenType,
        issuedAt: LocalDateTime = LocalDateTime.now()
    ): String {
        val expiresAt = issuedAt.plus(validityOf(tokenType))

        return Jwts.builder()
            .setSubject(subject.toString())
            .claim("tokenType", tokenType.name)
            .setIssuedAt(issuedAt.toDate())
            .setExpiration(expiresAt.toDate())
            .signWith(key)
            .compact()
    }

    private fun validityOf(type: TokenType): Duration =
        when (type) {
            TokenType.OTP -> jwtProperties.otpTokenValidity
            TokenType.PRE_AUTH -> jwtProperties.preAuthTokenValidity
            TokenType.ACCESS -> jwtProperties.accessTokenValidity
            TokenType.REFRESH -> jwtProperties.refreshTokenValidity
        }
}
