package `in`.ding.payment.domain.model

import `in`.ding.common.domain.DomainID
import `in`.ding.payment.domain.model.enumerate.PaymentStatus
import `in`.ding.payment.domain.model.enumerate.PaymentTransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Payment(
    val id: PaymentID,
    val exKey: UUID,
    val sellerExKey: UUID,
    val amount: BigDecimal,
    var status: PaymentStatus,
    val authorizedAt: LocalDateTime,
    var capturedAt: LocalDateTime? = null,
    var refundedAt: LocalDateTime? = null,
    private val transactions: MutableList<PaymentTransaction>,
) {

    companion object {
        fun authorize(
            sellerExKey: UUID,
            amount: BigDecimal
        ): Payment {
            require(amount > BigDecimal.ZERO)
            val exKey = UUID.randomUUID()
            val authorizedAt = LocalDateTime.now()
            val authTx = PaymentTransaction(
                id = DomainID.UNASSIGNED,
                type = PaymentTransactionType.AUTHORIZE,
                amount = amount,
            )
            return Payment(
                id = PaymentID.UNASSIGNED,
                exKey = exKey,
                sellerExKey = sellerExKey,
                amount = amount,
                status = PaymentStatus.AUTHORIZED,
                authorizedAt = authorizedAt,
                transactions = mutableListOf(authTx),
            )
        }
    }

    fun capture() {
        require(status == PaymentStatus.AUTHORIZED)

        transactions.add(
            PaymentTransaction(
                id = DomainID.UNASSIGNED,
                type = PaymentTransactionType.CAPTURE,
                amount = amount
            )
        )
        this.status = PaymentStatus.CAPTURED
        this.capturedAt = LocalDateTime.now()
    }
    fun getTransactions(): List<PaymentTransaction> {
        return this.transactions
    }
}
