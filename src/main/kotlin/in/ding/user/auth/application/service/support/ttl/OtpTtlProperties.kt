package `in`.ding.user.auth.application.service.support.ttl

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("otp.ttl")
class OtpTtlProperties(
    val session: Duration,
    val retryTrack: Duration,
    val block: Duration,
    val blacklist: Duration,
    val verifiedIdentity: Duration
)
