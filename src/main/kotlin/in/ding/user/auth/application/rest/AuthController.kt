package `in`.ding.user.auth.application.rest

import `in`.ding.common.infra.http.RequestContextHolder
import `in`.ding.common.infra.security.AuthUser
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
    @GetMapping("post-auth/status")
    fun getAuthStatus(
        @AuthenticationPrincipal user: AuthUser,
        @RequestParam nationality: String
    ): PostAuthStatusResponse {
        val ctx = RequestContextHolder.get()
        val nationality = UserNationality.from(nationality)
        return authService.getAuthStatus(
            GetPostAuthStatusQuery(user.exKey, PrincipalType.SELLER, ctx.clientPlatform, nationality)
        )
    }

    @GetMapping("/users/{userExKey}/token")
    fun getToken(@PathVariable userExKey: UUID): OtpVerifyResponse {
        return authService.issueTokenForTest(userExKey)
    }
}
