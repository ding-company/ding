package `in`.ding.point.domain.model

@JvmInline
value class PointConsumptionId(val value: Long) {
    companion object {
        val UNASSIGNED = PointConsumptionId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
