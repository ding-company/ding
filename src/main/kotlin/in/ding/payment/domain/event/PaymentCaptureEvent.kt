package `in`.ding.payment.domain.event

import `in`.ding.common.kafka.EventType
import java.time.LocalDateTime
data class PaymentCaptureEvent(
    override val eventType: EventType = EventType.UPDATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : PaymentEvent
