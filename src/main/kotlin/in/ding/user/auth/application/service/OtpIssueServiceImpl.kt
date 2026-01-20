package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.domain.event.OtpAbuseDetectedEvent
import `in`.ding.user.auth.domain.exception.OtpBlacklistedException
import `in`.ding.user.auth.domain.exception.TooManyOtpAttemptsException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.enumerate.OtpAvailability
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.ContactPolicy
import org.springframework.stereotype.Service

@Service
class OtpIssueServiceImpl(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val contactPolicy: ContactPolicy,
    private val publisher: AuthEventPublisher,
    private val otpRepository: RedisOtpRepository
) : OtpIssueService {
    override fun issue(command: OtpIssueCommand) {
        when (availabilityGuard.check(command.contact)) {
            OtpAvailability.BLOCKED -> {
                publisher.publish(OtpAbuseDetectedEvent(command.contact))
                throw TooManyOtpAttemptsException()
            }
            OtpAvailability.BLACKLISTED -> {
                throw OtpBlacklistedException()
            }
            OtpAvailability.OK -> {}
        }

        val otp = OtpSession.issue(
            contact = command.contact,
            code = OtpCode.generate(),
            contactType = contactPolicy.determineContactType(command.nationality)
        )

        otpRepository.saveOtp(otp.contact, otp, OtpSession.getOtpTtl())
        otp.drainEvents().forEach(publisher::publish)
    }
}
