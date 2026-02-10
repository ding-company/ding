package `in`.ding.common.infra.security.jwt.model

import java.time.LocalDateTime

data class IssuedToken(
    val token: String,
    val tokenType: TokenType,
    val issuedAt: LocalDateTime,
    val expiresInSeconds: Long,
    val absoluteExpiresAt: LocalDateTime? = null
)
