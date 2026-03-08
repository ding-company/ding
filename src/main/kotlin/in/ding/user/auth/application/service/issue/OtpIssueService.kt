package `in`.ding.user.auth.application.service.issue

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.common.infra.security.jwt.TokenIssuer
import `in`.ding.common.infra.security.jwt.model.BaseTokenResponse
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.user.auth.application.expiry.ExpiryResolver
import `in`.ding.user.auth.application.rest.response.OtpIssueResponse
import `in`.ding.user.auth.application.service.policy.OtpAvailabilityGuard
import `in`.ding.user.auth.domain.exception.NotFoundOtpException
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.user.domain.model.ContactPolicy
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OtpIssueService(
    private val tokenIssuer: TokenIssuer,
    private val availabilityGuard: OtpAvailabilityGuard,
    private val contactPolicy: ContactPolicy,
    private val publisher: EventPublisher,
    private val otpRepository: RedisOtpRepository,
    private val expiryResolver: ExpiryResolver
) {
    @Transactional
    fun issue(command: OtpIssueCommand): OtpIssueResponse {
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
        val token = tokenIssuer.issueAuthenticationToken(
            subject = otp.sessionId,
            tokenType = TokenType.OTP
        )
        otp.drainEvents().forEach(publisher::publish)
        return OtpIssueResponse(
            token = BaseTokenResponse(
                authenticationToken = token.token,
                refreshToken = null,
                expiresInSeconds = token.expiresInSeconds
            )
        )
    }

    // TODO max reIssue카운트 어떻게 할지 고려
    @Transactional
    fun reIssue(contact: String, nationality: UserNationality,) {
        val otp = otpRepository.findOtp(contact) ?: throw NotFoundOtpException()
        otp.reIssue(code = OtpCode.generate(), contactType = contactPolicy.determineContactType(nationality))
        val lifeTime = DomainLifetime.OTP_SESSION
        val ttl = expiryResolver.resolve(lifeTime)
        otpRepository.saveOtp(otp.contact, otp, ttl)
        otp.drainEvents().forEach(publisher::publish)
    }
}
