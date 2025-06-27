package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.domain.model.Otp
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.service.ContactValidator

class OtpServiceImpl(private val validator: ContactValidator, private val publisher: AuthEventPublisher) : OtpService {
    override fun issue(command: OtpIssueCommand) {
        val contact = validator.validate(
            nationality = command.nationality,
            phoneNumber = command.phoneNumber,
            email = command.email
        )
        val otp = Otp(contact = contact, nationality = command.nationality)
        otp.requestOtp(requestId = command.requestId)
        otp.events.forEach { publisher::publish }
        otp.clearEvents()
    }
    override fun verify(command: OtpVerifyCommand) {
        TODO()
    }
}
