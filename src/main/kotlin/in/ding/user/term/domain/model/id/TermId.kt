package `in`.ding.user.term.domain.model.id

@JvmInline
value class TermId(val value: Long) {
    companion object {
        val UNASSIGNED = TermId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
