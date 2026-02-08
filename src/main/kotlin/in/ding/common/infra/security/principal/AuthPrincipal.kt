package `in`.ding.common.infra.security.principal

import `in`.ding.common.infra.security.jwt.TokenType
import java.security.Principal
import java.util.UUID

data class AuthPrincipal(
    val subject: UUID,
    val tokenType: TokenType
) : Principal {
    override fun getName(): String {
        return subject.toString()
    }
}
