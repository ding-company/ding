package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.service.OtpVerifier
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher

class OtpServiceImpl(
    private val otpVerifier: OtpVerifier,
    private val tokenIssuer: TokenIssuer,
    private val publisher: AuthEventPublisher,
) {
    fun issue(command: OtpIssueCommand) {
        otpVerifier.checkAvailability(command.contact)

        publisher.publish(
            OtpRequestedEvent(
                contact = command.contact,
                nationality = command.nationality,
                requestId = command.requestId
            )
        )
    }

    fun verify(command: OtpVerifyCommand): OtpVerifyResponse {
        otpVerifier.checkAvailability(command.contact)

        val user = otpVerifier.verifyOtp(command)
        return OtpVerifyResponse.of(tokenIssuer.issueTokens(user.exKey))
    }
}
