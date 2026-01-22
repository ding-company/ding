package `in`.ding.user.auth.application.expiry

import `in`.ding.user.auth.application.service.policy.SecurityLifetime
import `in`.ding.user.auth.domain.policy.DomainLifetime
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ExpiryResolver(
    private val authExpiry: AuthExpiryProperties,
    private val securityExpiry: SecurityExpiryProperties
) {

    fun resolve(lifetime: DomainLifetime): Duration =
        when (lifetime) {
            DomainLifetime.OTP_SESSION ->
                authExpiry.otpSession

            DomainLifetime.VERIFIED_IDENTITY ->
                authExpiry.verifiedIdentity
        }
    fun resolve(lifetime: SecurityLifetime): Duration =
        when (lifetime) {
            SecurityLifetime.OTP_RETRY_TRACK -> securityExpiry.otpRetryTrack
            SecurityLifetime.OTP_BLOCK -> securityExpiry.otpBlock
            SecurityLifetime.OTP_BLACKLIST -> securityExpiry.otpBlacklist
        } }
