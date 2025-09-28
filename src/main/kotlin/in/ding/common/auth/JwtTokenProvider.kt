package `in`.ding.common.auth

import `in`.ding.user.user.domain.model.enumerate.UserStatus
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(userExKey: UUID, status: UserStatus): String {
        val now = Date()
        val validity = Date(now.time + jwtProperties.accessTokenValidity.toDays())

        return Jwts.builder()
            .setSubject(userExKey.toString())
            .claim("status", status.name)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshToken(userExKey: UUID, status: UserStatus): String {
        val now = Date()
        val validity = Date(now.time + jwtProperties.refreshTokenValidity.toDays())

        return Jwts.builder()
            .setSubject(userExKey.toString())
            .claim("status", status.name)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun extractUserExKey(token: String): UUID {
        val claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)

        return UUID.fromString(claims.body["sub"] as String)
    }
}
