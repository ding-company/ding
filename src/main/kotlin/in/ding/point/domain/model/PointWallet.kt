package `in`.ding.point.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
data class PointWallet(
    val id: PointWalletId,
    val customerExKey: UUID,
    var confirmedAmount: BigDecimal = BigDecimal.ZERO,
    var onHoldAmount: BigDecimal = BigDecimal.ZERO,
    var lastTransactionAt: LocalDateTime = LocalDateTime.now(),
    val ledgers: MutableList<PointLedger> = mutableListOf(),
    val consumptions: MutableList<PointConsumption> = mutableListOf()
) {
    companion object {
        fun init(customerExKey: UUID): PointWallet {
            return PointWallet(id = PointWalletId.UNASSIGNED, customerExKey = customerExKey)
        }
    }
    fun earn(amount: BigDecimal): PointLedger {
        val now = LocalDateTime.now()
        confirmedAmount += amount
        lastTransactionAt = now
        val ledger = PointLedger.earn(customerExKey, amount, now)
        ledgers.add(ledger)
        return ledger
    }

    fun use(amount: BigDecimal, refTxExKey: UUID): Pair<PointLedger, PointConsumption> {
        require(confirmedAmount >= amount) { "포인트 부족" }

        val now = LocalDateTime.now()
        confirmedAmount -= amount
        lastTransactionAt = now

        val ledger = PointLedger.use(customerExKey, amount, now, refTxExKey)
        val consumption = PointConsumption.create(ledger.exKey, refTxExKey, amount, now)

        ledgers.add(ledger)
        consumptions.add(consumption)

        return ledger to consumption
    }
}
