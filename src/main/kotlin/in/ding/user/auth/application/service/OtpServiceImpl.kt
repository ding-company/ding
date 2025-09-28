package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.service.OtpVerifier
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.exception.NotFoundUserException
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OtpServiceImpl(
    private val otpVerifier: OtpVerifier,
    private val tokenIssuer: TokenIssuer,
    private val publisher: AuthEventPublisher,
    private val userRedisRepository: UserRedisRepository,
) : OtpService {
    override fun issue(command: OtpIssueCommand) {
        otpVerifier.checkAvailability(command.contact)

        publisher.publish(
            OtpRequestedEvent(
                contact = command.contact,
                nationality = command.nationality,
                requestId = command.requestId
            )
        )
    }

    @Transactional
    override fun verify(command: OtpVerifyCommand): OtpVerifyResponse {
        otpVerifier.checkAvailability(command.contact)
        val savedUser = userRedisRepository.findTemporaryUser(command.contact) ?: throw NotFoundUserException()

        if (otpVerifier.verifyOtp(command)) {
            return OtpVerifyResponse.of(tokenIssuer.issueTokens(savedUser.exKey, UserStatus.TEMPORARY))
        } else {
            throw InvalidOtpException()
        }
    }

    override fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse.of(tokenIssuer.issueTokens(userExKey, UserStatus.REGISTERED))
    }
}
