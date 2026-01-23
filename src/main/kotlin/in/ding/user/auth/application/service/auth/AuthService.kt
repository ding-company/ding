package `in`.ding.user.auth.application.service.auth

import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val tokenIssuer: TokenIssuer,
) {
    fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse.Companion.of(tokenIssuer.issueTokens(userExKey, UserStatus.REGISTERED))
    }
}
