package `in`.ding.point.domain.model

@JvmInline
value class PointWalletId(val value: Long) {
    companion object {
        val UNASSIGNED = PointWalletId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
