package `in`.ding.user.user.application.dto.consumer

import java.util.UUID

class UserRegisterByCustomerEventCommand(
    val exKey: UUID,
    val phoneNumber: String?,
    val name: String?,
)
