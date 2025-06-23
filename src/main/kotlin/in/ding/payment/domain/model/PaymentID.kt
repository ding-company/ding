package `in`.ding.payment.domain.model

@JvmInline
value class PaymentID(val value: Long) {
    companion object {
        val UNASSIGNED = PaymentID(-1L)
    }

    fun isAssigned(): Boolean = value > 0
}
