package `in`.ding.common.auth

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

    fun generateAccessToken(userExKey: UUID): String {
        val claims = Jwts.claims().setSubject(userExKey.toString())
        val now = Date()
        val validity = Date(now.time + jwtProperties.accessTokenValidity.toMillis())

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256) // 2. 생성된 Key 객체 사용
            .compact()
    }

    fun generateRefreshToken(userExKey: UUID): String {
        val now = Date()
        val validity = Date(now.time + jwtProperties.refreshTokenValidity.toMillis())

        return Jwts.builder()
            .setSubject(userExKey.toString())
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
