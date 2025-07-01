package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.ContactPolicy

class OtpServiceImpl(private val validator: ContactPolicy, private val publisher: AuthEventPublisher) : OtpService {
    override fun issue(command: OtpIssueCommand) {
        val contact = validator.validate(
            nationality = command.nationality,
            phoneNumber = command.phoneNumber,
            email = command.email
        )
        publisher.publish(
            OtpRequestedEvent(
                contact = contact,
                nationality = command.nationality,
                requestId = command.requestId
            )
        )
    }
    override fun verify(command: OtpVerifyCommand) {
        TODO()
    }
}
