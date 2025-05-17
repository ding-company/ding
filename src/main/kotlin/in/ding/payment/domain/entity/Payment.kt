package `in`.ding.payment.domain.entity

import `in`.ding.payment.domain.entity.enumerate.PaymentStatus
import `in`.ding.payment.domain.entity.enumerate.PaymentTransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Payment(
    val id: UUID,
    val sellerExKey: UUID,
    val amount: BigDecimal,
    var status: PaymentStatus,
    val authorizedAt: LocalDateTime,
    var capturedAt: LocalDateTime? = null,
    var refundedAt: LocalDateTime? = null,
    private val transactions: MutableList<PaymentTransaction>,
    private val newTransactions: MutableList<PaymentTransaction> = mutableListOf()
) {

    companion object {
        fun authorize(
            sellerExKey: UUID,
            amount: BigDecimal
        ): Payment {
            require(amount > BigDecimal.ZERO)
            val paymentId = UUID.randomUUID()
            val authorizedAt = LocalDateTime.now()
            val authTx = PaymentTransaction(
                type = PaymentTransactionType.AUTHORIZE,
                amount = amount,
            )
            return Payment(
                id = paymentId,
                sellerExKey = sellerExKey,
                amount = amount,
                status = PaymentStatus.AUTHORIZED,
                authorizedAt = authorizedAt,
                transactions = mutableListOf(),
                newTransactions = mutableListOf(authTx)
            )
        }
    }

    fun capture() {
        require(status == PaymentStatus.AUTHORIZED)

        newTransactions.add(
            PaymentTransaction(
                type = PaymentTransactionType.CAPTURE,
                amount = amount
            )
        )
        this.status = PaymentStatus.CAPTURED
        this.capturedAt = LocalDateTime.now()
    }
    fun getNewTransactions(): List<PaymentTransaction> {
        return this.newTransactions
    }
    fun getTransactions(): List<PaymentTransaction> {
        return this.newTransactions
    }
}
