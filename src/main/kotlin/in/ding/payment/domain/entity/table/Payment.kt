package `in`.ding.payment.domain.entity.table

import `in`.ding.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "payments")
class Payment(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36)
    val sellerExKey: UUID,

    @Column()
    val amount: BigDecimal,

    @Column()
    val transactionAt: LocalDateTime,
) : BaseEntity() {
    companion object {
        fun capture(sellerExKey: UUID, amount: BigDecimal): Payment {
            return Payment(UUID.randomUUID(), sellerExKey, amount, transactionAt = LocalDateTime.now(),)
        }
    }
}
