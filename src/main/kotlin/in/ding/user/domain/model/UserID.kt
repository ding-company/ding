package `in`.ding.user.domain.model

@JvmInline
value class UserID(val value: Long) {
    companion object {
        val UNASSIGNED = UserID(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
