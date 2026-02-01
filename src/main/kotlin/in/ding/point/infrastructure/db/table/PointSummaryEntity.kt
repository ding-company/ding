package `in`.ding.point.infrastructure.db.table

import `in`.ding.common.infra.jpa.SoftDeletedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID
@Table(name = "point_summaries")
data class PointSummaryEntity(
    @Column(unique = true, length = 36)
    val pointExKey: UUID,

    @Column(length = 36, nullable = false)
    val customerExKey: UUID,

    @Column(nullable = false)
    var totalEarnedAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var totalUsedAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var totalExpiredAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var totalDeductedAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var totalReversedAmount: BigDecimal = BigDecimal.ZERO,
) : SoftDeletedBaseEntity()
