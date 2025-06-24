package `in`.ding.point.domain.model

import java.math.BigDecimal
import java.util.UUID

data class PointSummary(
    val id: PointSummaryId = PointSummaryId.UNASSIGNED,
    val customerExKey: UUID,
    var totalEarned: BigDecimal = BigDecimal.ZERO,
    var totalUsed: BigDecimal = BigDecimal.ZERO,
    var totalExpired: BigDecimal = BigDecimal.ZERO,
    var totalReversed: BigDecimal = BigDecimal.ZERO
) {
    companion object {
        fun create(customerExKey: UUID): PointSummary {
            return PointSummary(customerExKey = customerExKey)
        }
    }
}
