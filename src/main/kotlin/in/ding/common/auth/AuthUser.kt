package `in`.ding.common.auth

import `in`.ding.user.user.domain.model.enumerate.UserStatus
import java.security.Principal
import java.util.UUID

data class AuthUser(
    val exKey: UUID,
    val status: UserStatus,
) : Principal {
    override fun getName(): String {
        return exKey.toString()
    }
}
