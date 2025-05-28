package `in`.ding.point.infrastructure.db.table

import `in`.ding.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "point_consumptions")
data class PointConsumptionEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36, nullable = false)
    val consumeTxExKey: UUID,

    @Column(length = 36, nullable = false)
    val earnTxExKey: UUID,

    @Column(nullable = false)
    var amount: BigDecimal,

    @Column(nullable = false)
    var transactionAt: LocalDateTime,
) : BaseEntity()
