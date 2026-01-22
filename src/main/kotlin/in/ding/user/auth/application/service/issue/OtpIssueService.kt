package `in`.ding.user.auth.application.service.issue

import `in`.ding.user.auth.application.service.policy.OtpAvailabilityGuard
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.ContactPolicy
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OtpIssueService(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val contactPolicy: ContactPolicy,
    private val publisher: AuthEventPublisher,
    private val otpRepository: RedisOtpRepository
) {
    @Transactional
    fun issue(command: OtpIssueCommand) {
        availabilityGuard.check(command.contact)
        val otp = OtpSession.issue(
            contact = command.contact,
            code = OtpCode.generate(),
            contactType = contactPolicy.determineContactType(command.nationality)
        )

        otpRepository.saveOtp(otp.contact, otp, OtpSession.getOtpTtl())
        otp.drainEvents().forEach(publisher::publish)
    }
}
