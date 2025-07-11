package `in`.ding.user.term.domain.model.id

@JvmInline
value class TermAgreementId(val value: Long) {
    companion object {
        val UNASSIGNED = TermAgreementId(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
