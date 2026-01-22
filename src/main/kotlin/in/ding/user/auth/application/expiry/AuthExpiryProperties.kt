package `in`.ding.user.auth.application.expiry

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "auth.expiry")
data class AuthExpiryProperties(
    val otpSession: Duration,
    val verifiedIdentity: Duration
)
