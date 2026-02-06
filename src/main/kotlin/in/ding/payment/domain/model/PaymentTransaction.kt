package `in`.ding.payment.domain.model

import `in`.ding.common.domain.DomainID
import `in`.ding.payment.domain.model.enumerate.PaymentTransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class PaymentTransaction(
    val id: DomainID,

    val exKey: UUID = UUID.randomUUID(),

    val amount: BigDecimal,

    val type: PaymentTransactionType,

    val transactionAt: LocalDateTime = LocalDateTime.now(),
) {
    fun isNew(): Boolean = !id.isAssigned()
}
