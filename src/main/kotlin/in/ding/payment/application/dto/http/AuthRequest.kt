package `in`.ding.payment.application.dto.http

import java.math.BigDecimal
import java.util.UUID

data class AuthRequest(
    val customerPhoneNumber: String?,
    val amount: BigDecimal,

    val usedPointAmount: BigDecimal = BigDecimal.ZERO,
    val usedCouponExKeys: List<UUID> = emptyList(),

    val manualPointEarnAmount: BigDecimal? = null,
    val manualStampEarnCount: Int? = null,

    val isUseAutoPointReward: Boolean = false,
    val isUseAutoStampReward: Boolean = false
)
