package `in`.ding.point.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Point(
    val customerExKey: UUID,

    var confirmedAmount: BigDecimal = BigDecimal.ZERO,

    var onHoldAmount: BigDecimal = BigDecimal.ZERO,
    var lastTransactionAt: LocalDateTime = LocalDateTime.now(),
)
