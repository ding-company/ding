package `in`.ding.common.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
    lateinit var accessTokenValidity: Duration
    lateinit var refreshTokenValidity: Duration
}
