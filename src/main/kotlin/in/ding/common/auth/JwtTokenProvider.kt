package `in`.ding.common.auth

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String
) {
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun validateToken(token: String): Boolean {
        try {
            val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    fun extractUserExKey(token: String): UUID {
        val claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)

        return UUID.fromString(claims.body["userExKey"] as String)
    }
}
