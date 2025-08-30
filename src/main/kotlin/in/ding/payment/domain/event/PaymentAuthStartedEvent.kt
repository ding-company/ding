package `in`.ding.payment.domain.event

import `in`.ding.common.kafka.EventType
import `in`.ding.payment.application.dto.command.AuthCommand
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class PaymentAuthStartedEvent(
    val paymentExKey: UUID,
    val customerPhoneNumber: String?,
    val sellerExKey: UUID,
    val amount: BigDecimal,

    val usedPointAmount: BigDecimal = BigDecimal.ZERO,
    val usedCouponExKeys: List<UUID> = emptyList(),

    val manualPointEarnAmount: BigDecimal? = null,
    val manualStampEarnCount: Int? = null,

    val isUseAutoPointReward: Boolean = false,
    val isUseAutoStampReward: Boolean = false,
    override val eventName: String = "payment_auth_started_event",
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : PaymentEvent {
    companion object {
        fun of(paymentExKey: UUID, command: AuthCommand): PaymentAuthStartedEvent {
            return PaymentAuthStartedEvent(
                paymentExKey = paymentExKey,
                sellerExKey = command.sellerExKey,
                customerPhoneNumber = command.customerPhoneNumber,
                amount = command.amount,
                usedPointAmount = command.usedPointAmount,
                usedCouponExKeys = command.usedCouponExKeys,
                manualPointEarnAmount = command.manualPointEarnAmount,
                isUseAutoPointReward = command.isUseAutoPointReward,
                isUseAutoStampReward = command.isUseAutoStampReward,
            )
        }
    }
}
