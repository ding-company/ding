package `in`.ding.common.auth

import java.security.Principal
import java.util.UUID

data class AuthUser(
    val exKey: UUID
) : Principal {
    override fun getName(): String {
        return exKey.toString()
    }
}
