package `in`.ding.user.auth.application.service.verify

import `in`.ding.common.infra.event.EventPublisher
import `in`.ding.common.infra.http.exception.BaseHttpException
import `in`.ding.common.infra.security.jwt.TokenIssuer
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.user.auth.application.expiry.ExpiryResolver
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.service.policy.OtpAvailabilityGuard
import `in`.ding.user.auth.application.service.policy.OtpFailureProcessor
import `in`.ding.user.auth.application.service.support.VerifiedIdentityAppService
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.domain.policy.DomainLifetime
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OtpVerifyService(
    private val availabilityGuard: OtpAvailabilityGuard,
    private val otpRepository: RedisOtpRepository,
    private val verifiedIdentityFactory: VerifiedIdentityAppService,
    private val tokenIssuer: TokenIssuer,
    private val publisher: EventPublisher,
    private val otpFailureProcessor: OtpFailureProcessor,
    private val expiryResolver: ExpiryResolver
) {

    @Transactional
    fun verify(command: OtpVerifyCommand): OtpVerifyResponse {
        availabilityGuard.check(command.contact)
        val otp = otpRepository.findOtp(command.contact)
            ?: throw OtpNotFound()

        val updatedOtp = try {
            otp.verify(command.otpCode, command.nationality)
        } catch (e: BaseHttpException) {
            otpFailureProcessor.handle(otp, command.contact)
            throw e
        }
        val lifeTime = DomainLifetime.OTP_SESSION
        val ttl = expiryResolver.resolve(lifeTime)

        otpRepository.saveOtp(
            command.contact,
            updatedOtp,
            ttl
        )

        val verifiedIdentity = verifiedIdentityFactory.create(command.contact, command.nationality)

        updatedOtp.drainEvents().forEach(publisher::publish)

        return OtpVerifyResponse(
            tokenIssuer.issueAuthenticationToken(verifiedIdentity.exKey, tokenType = TokenType.OTP).token
        )
    }
}
