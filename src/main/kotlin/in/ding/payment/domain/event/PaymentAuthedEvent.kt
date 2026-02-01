package `in`.ding.payment.domain.event

import `in`.ding.common.event.EventType
import java.math.BigDecimal
import java.util.UUID

data class PaymentAuthedEvent(
    val paymentExKey: UUID,
    val customerExKey: UUID,
    val sellerExKey: UUID,
    val amount: BigDecimal,

    val usedPointAmount: BigDecimal = BigDecimal.ZERO,
    val usedCouponExKeys: List<UUID> = emptyList(),

    val manualPointEarnAmount: BigDecimal? = null,
    val manualStampEarnCount: Int? = null,

    val isUseAutoPointReward: Boolean = false,
    val isUseAutoStampReward: Boolean = false,
    override val eventName: String = "payment_authed_event",
    override val eventType: EventType = EventType.CREATED,
) : PaymentBaseEvent(eventName, eventType) {
    companion object {
        fun of(customerExKey: UUID, authStartedEvent: PaymentAuthStartedEvent): PaymentAuthedEvent {
            return PaymentAuthedEvent(
                paymentExKey = authStartedEvent.paymentExKey,
                sellerExKey = authStartedEvent.sellerExKey,
                customerExKey = customerExKey,
                amount = authStartedEvent.amount,
                usedPointAmount = authStartedEvent.usedPointAmount,
                usedCouponExKeys = authStartedEvent.usedCouponExKeys,
                manualPointEarnAmount = authStartedEvent.manualPointEarnAmount,
                isUseAutoPointReward = authStartedEvent.isUseAutoPointReward,
                isUseAutoStampReward = authStartedEvent.isUseAutoStampReward,
            )
        }
    }
}
