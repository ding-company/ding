package `in`.ding.user.application.consumer.dto

import java.util.*

class UserRegisterByCustomerEventCommand(
    val exKey: UUID,
    val phoneNumber: String?,
    val name: String?,
)
