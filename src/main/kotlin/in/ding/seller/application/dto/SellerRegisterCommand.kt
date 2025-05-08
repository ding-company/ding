package `in`.ding.seller.application.dto

import java.util.UUID

data class SellerRegisterCommand(
    val userExKey: UUID,
    val storeName: String,
)
