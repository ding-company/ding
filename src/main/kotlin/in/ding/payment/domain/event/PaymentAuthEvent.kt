package `in`.ding.payment.domain.event

import `in`.ding.common.kafka.EventType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PaymentAuthEvent(
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
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : PaymentEvent {
    companion object {
        fun of(customerExKey: UUID, authStartedEvent: PaymentAuthStartedEvent): PaymentAuthEvent {
            return PaymentAuthEvent(
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
