package `in`.ding.payment.domain.entity

import `in`.ding.payment.domain.entity.enumerate.PaymentTransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class PaymentTransaction(
    val exKey: UUID = UUID.randomUUID(),

    val amount: BigDecimal,

    val type: PaymentTransactionType,

    val transactionAt: LocalDateTime = LocalDateTime.now(),
)
