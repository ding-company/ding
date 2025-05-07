package `in`.ding.customer.domain.service.dto

import `in`.ding.user.domain.entity.enumerate.UserNationality
import java.util.*

data class CustomerRegisterDTO(
    val phoneNumber: String?,
    val email: String?,
    val name: String?,
    val nationality: UserNationality,
    val userExKey: UUID,
    val sellerExKey: UUID,
)
