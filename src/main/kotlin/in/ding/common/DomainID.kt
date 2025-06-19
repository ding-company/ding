package `in`.ding.common

@JvmInline
value class DomainID(val value: Long) {
    companion object {
        val UNASSIGNED = DomainID(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
