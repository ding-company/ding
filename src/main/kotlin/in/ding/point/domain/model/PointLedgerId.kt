package `in`.ding.point.domain.model

@JvmInline
value class PointLedgerId(val value: Long) {
    companion object {
        val UNASSIGNED = PointLedgerId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
