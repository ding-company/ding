package `in`.ding.user.auth.application.rest

import `in`.ding.common.infra.http.RequestContextHolder
import `in`.ding.common.infra.security.jwt.JwtTokenProvider
import `in`.ding.common.infra.security.jwt.exception.InvalidTokenException
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.rest.response.PostAuthStatusResponse
import `in`.ding.user.auth.application.service.auth.AuthService
import `in`.ding.user.auth.application.service.auth.GetPostAuthStatusQuery
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenProvider: JwtTokenProvider,
) {
    @GetMapping("post-auth/status")
    fun getAuthStatus(
        @RequestHeader("Authorization") token: String,
        @RequestParam nationality: String
    ): PostAuthStatusResponse {
        val ctx = RequestContextHolder.get()
        val userNationality = UserNationality.from(nationality)
        val payload = tokenProvider.parse(token)
        if (payload.tokenType != TokenType.OTP) {
            throw InvalidTokenException()
        }
        return authService.getAuthStatus(
            GetPostAuthStatusQuery(payload.subject, PrincipalType.SELLER, ctx.clientPlatform, userNationality)
        )
    }

    @GetMapping("/users/{userExKey}/token")
    fun getToken(@PathVariable userExKey: UUID): OtpVerifyResponse {
        return authService.issueTokenForTest(userExKey)
    }
}
