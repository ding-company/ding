package `in`.ding.common.infra.event

import `in`.ding.common.domain.event.EventContext
import org.springframework.stereotype.Component

@Component
class EventTopicResolver(
    private val properties: EventTopicsProperties
) {
    fun resolve(context: EventContext): String =
        when (context) {
            EventContext.AUTH -> properties.auth
            EventContext.USER -> properties.user
            EventContext.TERM -> properties.term
            EventContext.PAYMENT -> properties.payment
            EventContext.CUSTOMER -> properties.customer
        }
}
