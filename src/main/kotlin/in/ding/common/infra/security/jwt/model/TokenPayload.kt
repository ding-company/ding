package `in`.ding.common.infra.security.jwt.model

import java.time.LocalDateTime
import java.util.UUID

data class TokenPayload(
    val subject: UUID,
    val tokenType: TokenType,
    val expiresAt: LocalDateTime
)
