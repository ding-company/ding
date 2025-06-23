package `in`.ding.customer.domain.model

@JvmInline
value class CustomerID(val value: Long) {
    companion object {
        val UNASSIGNED = CustomerID(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
