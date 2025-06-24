package `in`.ding.point.domain.model

import `in`.ding.point.domain.model.enumerate.PointTransactionSourceType
import `in`.ding.point.domain.model.enumerate.PointTransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PointLedger(
    val id: PointLedgerId,
    val exKey: UUID = UUID.randomUUID(),
    val customerExKey: UUID,
    val type: PointTransactionType,
    val amount: BigDecimal,
    val occurredAt: LocalDateTime,
    val sourceType: PointTransactionSourceType = PointTransactionSourceType.PAYMENT,
    val referenceTxExKey: UUID? = null,
) {
    companion object {
        fun earn(customerExKey: UUID, amount: BigDecimal, at: LocalDateTime) =
            PointLedger(
                id = PointLedgerId.UNASSIGNED,
                customerExKey = customerExKey,
                type = PointTransactionType.EARN,
                amount = amount,
                occurredAt = at
            )

        fun use(customerExKey: UUID, amount: BigDecimal, at: LocalDateTime, referenceTxExKey: UUID) =
            PointLedger(
                id = PointLedgerId.UNASSIGNED,
                customerExKey = customerExKey,
                type = PointTransactionType.USE,
                amount = amount.negate(),
                occurredAt = at,
                referenceTxExKey = referenceTxExKey
            )
    }
}
