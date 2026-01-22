package `in`.ding.user.auth.application.service.issue

import `in`.ding.user.auth.application.expiry.ExpiryResolver
import `in`.ding.user.auth.application.service.policy.OtpAvailabilityGuard
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.ContactPolicy
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OtpIssueService(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val contactPolicy: ContactPolicy,
    private val publisher: AuthEventPublisher,
    private val otpRepository: RedisOtpRepository,
    private val expiryResolver: ExpiryResolver
) {
    @Transactional
    fun issue(command: OtpIssueCommand) {
        availabilityGuard.check(command.contact)
        val now = LocalDateTime.now()
        val lifeTime = DomainLifetime.OTP_SESSION
        val ttl = expiryResolver.resolve(lifeTime)
        val otp = OtpSession.issue(
            contact = command.contact,
            code = OtpCode.generate(),
            contactType = contactPolicy.determineContactType(command.nationality),
            now = now,
            expiredCondition = ttl
        )

        otpRepository.saveOtp(otp.contact, otp, ttl)
        otp.drainEvents().forEach(publisher::publish)
    }
}
