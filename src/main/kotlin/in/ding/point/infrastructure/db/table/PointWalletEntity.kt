package `in`.ding.point.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
@Table(name = "point_wallets")
data class PointWalletEntity(
    @Column(unique = true, length = 36)
    val customerExKey: UUID,

    @Column(nullable = false)
    var confirmedAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var onHoldAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var lastTransactionAt: LocalDateTime = LocalDateTime.now(),
) : SoftDeletedBaseEntity()
