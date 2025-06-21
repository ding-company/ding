package `in`.ding.payment.application.dto.command

import `in`.ding.payment.application.dto.http.AuthRequest
import java.math.BigDecimal
import java.util.UUID

data class AuthCommand(
    val sellerExKey: UUID,
    val customerPhoneNumber: String?,
    val amount: BigDecimal,

    val usedPointAmount: BigDecimal = BigDecimal.ZERO,
    val usedCouponExKeys: List<UUID> = emptyList(),

    val manualPointEarnAmount: BigDecimal? = null,
    val manualStampEarnCount: Int? = null,

    val isUseAutoPointReward: Boolean = false,
    val isUseAutoStampReward: Boolean = false
) {
    companion object {
        fun of(sellerExKey: UUID, request: AuthRequest): AuthCommand {
            return AuthCommand(
                sellerExKey = sellerExKey,
                customerPhoneNumber = request.customerPhoneNumber,
                amount = request.amount,
                usedPointAmount = request.usedPointAmount,
                usedCouponExKeys = emptyList(),
                manualPointEarnAmount = request.usedPointAmount,
                manualStampEarnCount = request.manualStampEarnCount,
                isUseAutoPointReward = request.isUseAutoPointReward,
                isUseAutoStampReward = request.isUseAutoStampReward
            )
        }
    }
}
