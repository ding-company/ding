package `in`.ding.point.domain.model

@JvmInline
value class PointSummaryId(val value: Long) {
    companion object {
        val UNASSIGNED = PointSummaryId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
