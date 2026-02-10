package `in`.ding.user.auth.domain.model

import `in`.ding.common.infra.security.jwt.model.TokenType
import java.time.LocalDateTime
import java.util.UUID

data class RefreshToken(
    val token: String,
    val subject: UUID,
    val tokenType: TokenType,
    val expiresAt: LocalDateTime,
    val absoluteExpiresAt: LocalDateTime?,
    val isUsed: Boolean,
)
