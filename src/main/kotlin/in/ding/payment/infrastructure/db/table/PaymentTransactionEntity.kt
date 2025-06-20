package `in`.ding.payment.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.payment.domain.model.enumerate.PaymentTransactionType
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "payment_transactions")
class PaymentTransactionEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID = UUID.randomUUID(),

    @Column(length = 36)
    val paymentExKey: UUID,

    @Column()
    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val type: PaymentTransactionType,

    @Column()
    val transactionAt: LocalDateTime = LocalDateTime.now(),
) : SoftDeletedBaseEntity()
