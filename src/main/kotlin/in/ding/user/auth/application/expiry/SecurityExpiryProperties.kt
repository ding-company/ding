package `in`.ding.user.auth.application.expiry

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "security.expiry")
data class SecurityExpiryProperties(
    val otpBlock: Duration,
    val otpBlacklist: Duration,
    val otpRetryTrack: Duration,
)
