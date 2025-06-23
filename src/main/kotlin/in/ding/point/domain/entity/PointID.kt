package `in`.ding.point.domain.entity

@JvmInline
value class PointID(val value: Long) {
    companion object {
        val UNASSIGNED = PointID(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
