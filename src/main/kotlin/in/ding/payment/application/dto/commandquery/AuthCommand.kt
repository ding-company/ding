package `in`.ding.payment.application.dto.commandquery

import java.math.BigDecimal
import java.util.UUID

data class AuthCommand(
    val sellerExKey: UUID,
    val amount: BigDecimal,
)
