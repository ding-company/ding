package `in`.ding.payment.infrastructure.db.table

import `in`.ding.common.infra.jpa.SoftDeletedBaseEntity
import `in`.ding.payment.domain.model.enumerate.PaymentStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "payments")
class PaymentEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36)
    val sellerExKey: UUID,

    @Column()
    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var status: PaymentStatus,

    @Column()
    val authorizedAt: LocalDateTime,

    @Column()
    var capturedAt: LocalDateTime? = null,

    @Column
    var refundedAt: LocalDateTime? = null,
) : SoftDeletedBaseEntity()
