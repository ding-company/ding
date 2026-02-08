package `in`.ding.common.infra.security.jwt

import `in`.ding.common.infra.security.principal.AuthPrincipal
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.Duration
import java.util.Date
import java.util.UUID
@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val secretKey: Key =
        Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    fun generate(
        subject: UUID,
        tokenType: TokenType,
        validity: Duration
    ): String {
        val now = Date()
        val expiry = Date(now.time + validity.toMillis())

        return Jwts.builder()
            .setSubject(subject.toString())
            .claim("token_type", tokenType.name)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validate(token: String): Boolean =
        runCatching {
            !parse(token).expiration.before(Date())
        }.getOrElse { false }

    fun getAuthentication(token: String): Authentication {
        val claims = parse(token)

        val principal = AuthPrincipal(
            subject = UUID.fromString(claims.subject),
            tokenType = TokenType.valueOf(claims["token_type"].toString())
        )

        val authority = SimpleGrantedAuthority("TOKEN_${principal.tokenType.name}")

        return UsernamePasswordAuthenticationToken(
            principal,
            token,
            listOf(authority)
        )
    }

    private fun parse(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
}
