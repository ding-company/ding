package `in`.ding.common.infra.security.jwt

import `in`.ding.common.infra.security.jwt.exception.InvalidTokenException
import `in`.ding.common.infra.security.jwt.exception.TokenExpiredException
import `in`.ding.common.infra.security.jwt.model.TokenPayload
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.common.infra.security.principal.AuthPrincipal
import `in`.ding.common.toLocalDateTime
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    @Suppress("SwallowedException")
    fun parse(token: String): TokenPayload {
        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

            return TokenPayload(
                subject = UUID.fromString(claims.subject),
                tokenType = TokenType.valueOf(claims["tokenType"].toString()),
                expiresAt = claims.expiration.toLocalDateTime(),
            )
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException()
        } catch (e: JwtException) {
            throw InvalidTokenException()
        }
    }
    fun toAuthentication(payload: TokenPayload): Authentication {
        val principal = AuthPrincipal(
            subject = payload.subject,
            tokenType = payload.tokenType
        )

        return UsernamePasswordAuthenticationToken(
            principal,
            null,
            emptyList()
        )
    }
}
