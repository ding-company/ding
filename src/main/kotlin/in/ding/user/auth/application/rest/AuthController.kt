package `in`.ding.user.auth.application.rest

import `in`.ding.common.infra.http.RequestContextHolder
import `in`.ding.common.infra.security.principal.AuthPrincipal
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.rest.response.PostAuthStatusResponse
import `in`.ding.user.auth.application.service.auth.AuthService
import `in`.ding.user.auth.application.service.auth.GetPostAuthStatusQuery
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    // TODO Auth
    @GetMapping("post-auth/status")
    fun getAuthStatus(
        @AuthenticationPrincipal authPrincipal: AuthPrincipal,
        @RequestParam nationality: String
    ): PostAuthStatusResponse {
        val ctx = RequestContextHolder.get()
        val userNationality = UserNationality.from(nationality)
        return authService.getAuthStatus(
            GetPostAuthStatusQuery(authPrincipal.subject, PrincipalType.SELLER, ctx.clientPlatform, userNationality)
        )
    }

    @GetMapping("/users/{userExKey}/token")
    fun getToken(@PathVariable userExKey: UUID): OtpVerifyResponse {
        return authService.issueTokenForTest(userExKey)
    }
}
