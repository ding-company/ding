package `in`.ding.common.infra.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
    lateinit var otpTokenValidity: Duration
    lateinit var preAuthTokenValidity: Duration
    lateinit var accessTokenValidity: Duration
}
