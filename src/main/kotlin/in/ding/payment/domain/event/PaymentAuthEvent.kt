package `in`.ding.payment.domain.event

import `in`.ding.common.kafka.EventType
import java.time.LocalDateTime

data class PaymentAuthEvent(
    override val eventType: EventType = EventType.CREATED,
    override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : PaymentEvent
