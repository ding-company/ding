package `in`.ding.common.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID
@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    fun generateAccessToken(userExKey: UUID): String {
        val claims = Jwts.claims().setSubject(userExKey.toString())
        val now = Date()
        val validity = Date(now.time + jwtProperties.accessTokenValidity.toMillis())

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
            .compact()
    }

    fun generateRefreshToken(userExKey: UUID): String {
        val now = Date()
        val validity = Date(now.time + jwtProperties.refreshTokenValidity.toMillis())

        return Jwts.builder()
            .setSubject(userExKey.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
            .compact()
    }

    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun extractUserExKey(token: String): UUID {
        val claims = Jwts.parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)

        return UUID.fromString(claims.body["userExKey"] as String)
    }
}
