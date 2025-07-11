package `in`.ding.user.user.application.dto.http

import `in`.ding.user.user.domain.model.enumerate.UserStatus
import java.util.UUID

data class UserResponse(
    val exKey: UUID,
    val status: UserStatus,
)
