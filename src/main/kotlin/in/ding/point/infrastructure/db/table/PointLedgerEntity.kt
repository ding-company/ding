package `in`.ding.point.infrastructure.db.table

import `in`.ding.common.BaseEntity
import `in`.ding.point.domain.entity.enumerate.PointTransactionSourceType
import `in`.ding.point.domain.entity.enumerate.PointTransactionType
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
@Table(name = "point_ledgers")
data class PointLedgerEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36, nullable = false)
    val pointExKey: UUID,

    @Column(nullable = false)
    val type: PointTransactionType,

    @Column(nullable = false)
    val sourceType: PointTransactionSourceType,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val occurredAt: LocalDateTime,

    @Column(nullable = true)
    val expiresAt: LocalDateTime? = null,

    @Column(nullable = false)
    val isEffective: Boolean = true,

    @Column(nullable = false)
    val visibleToCustomer: Boolean = true,

    @Column(nullable = false)
    val balanceAfterTx: BigDecimal,

    @Column(length = 36, nullable = true)
    val referenceTxExKey: UUID? = null,

    @Column(length = 36, nullable = true)
    val relatedTxExKey: UUID? = null,
) : BaseEntity()
