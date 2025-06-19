package `in`.ding.point.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.point.domain.entity.enumerate.PointTransactionType
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "customer_point_history_views")
data class CustomerPointHistoryViewEntity(
    @Column(length = 36, nullable = false)
    val customerExKey: UUID,

    @Column(length = 36, nullable = false)
    val pointTxExKey: UUID,

    @Column(nullable = false)
    val type: PointTransactionType,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val occurredAt: LocalDateTime,

    @Column(nullable = true)
    val sourceLabel: String? = null,

    @Column(nullable = true)
    val refId: String? = null,
) : SoftDeletedBaseEntity()
