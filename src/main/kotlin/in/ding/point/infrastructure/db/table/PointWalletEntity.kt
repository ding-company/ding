package `in`.ding.point.infrastructure.db.table

import `in`.ding.common.BaseEntity
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
    var confirmedAmount: BigDecimal = BigDecimal.ZERO, // 실제 사용 가능한 잔액

    @Column(nullable = false)
    var onHoldAmount: BigDecimal = BigDecimal.ZERO, // 사용 예정 금액 (결제 진행중 등)

    @Column(nullable = false)
    var lastTransactionAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity()
