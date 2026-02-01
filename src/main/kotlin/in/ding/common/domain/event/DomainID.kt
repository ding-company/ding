package `in`.ding.common.domain.event

@JvmInline
value class DomainID(val value: Long) {
    companion object {
        val UNASSIGNED = DomainID(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
