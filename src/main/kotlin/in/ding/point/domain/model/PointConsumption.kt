package `in`.ding.point.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PointConsumption(
    val id: PointConsumptionId,
    val exKey: UUID = UUID.randomUUID(),
    val earnTxExKey: UUID,
    val consumeTxExKey: UUID,
    val amount: BigDecimal,
    val transactionAt: LocalDateTime
) {
    companion object {
        fun create(earnTxExKey: UUID, consumeTxExKey: UUID, amount: BigDecimal, at: LocalDateTime) =
            PointConsumption(
                id = PointConsumptionId.UNASSIGNED,
                earnTxExKey = earnTxExKey,
                consumeTxExKey = consumeTxExKey,
                amount = amount,
                transactionAt = at
            )
    }
}
