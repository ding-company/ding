package `in`.ding.payment.domain.event

import `in`.ding.common.kafka.EventType
import java.time.LocalDateTime
data class PaymentCapturedEvent(
    override val eventName: String = "payment_captured_event",
    override val eventType: EventType = EventType.UPDATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : PaymentEvent
