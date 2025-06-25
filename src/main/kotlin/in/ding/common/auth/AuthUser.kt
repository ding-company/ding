package `in`.ding.common.auth

import java.security.Principal
import java.util.UUID

data class AuthUser(
    val userExKey: UUID
) : Principal {
    override fun getName(): String {
        return userExKey.toString()
    }
}
