package `in`.ding.common.infra.security.jwt

import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenIssuer(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtProperties: JwtProperties
) {

    fun issueOtpToken(subject: UUID): String =
        jwtTokenProvider.generate(
            subject,
            TokenType.OTP,
            jwtProperties.otpTokenValidity
        )

    fun issuePreAuthToken(subject: UUID): String =
        jwtTokenProvider.generate(
            subject,
            TokenType.PRE_AUTH,
            jwtProperties.preAuthTokenValidity
        )

    fun issueAccessToken(subject: UUID): String =
        jwtTokenProvider.generate(
            subject,
            TokenType.AUTHENTICATED,
            jwtProperties.accessTokenValidity
        )
}
