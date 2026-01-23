package `in`.ding.user.auth.application.rest

import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.rest.response.PostAuthStatus
import `in`.ding.user.auth.application.rest.response.PostAuthStatusResponse
import `in`.ding.user.auth.application.service.auth.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("post-auth/status")
    fun postAuth(): PostAuthStatusResponse {
        return PostAuthStatusResponse(PostAuthStatus.AUTHENTICATED)
    }

    @GetMapping("/users/{userExKey}/token")
    fun getToken(@PathVariable userExKey: UUID): OtpVerifyResponse {
        return authService.issueTokenForTest(userExKey)
    }
}
