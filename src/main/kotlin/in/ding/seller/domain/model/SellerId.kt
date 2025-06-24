package `in`.ding.seller.domain.model

@JvmInline
value class SellerId(val value: Long) {
    companion object {
        val UNASSIGNED = SellerId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
