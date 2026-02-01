package `in`.ding.payment.domain.event

import `in`.ding.common.event.EventType
data class PaymentCapturedEvent(
    override val eventName: String = "payment_captured_event",
    override val eventType: EventType = EventType.UPDATED,
) : PaymentBaseEvent(eventName, eventType)
