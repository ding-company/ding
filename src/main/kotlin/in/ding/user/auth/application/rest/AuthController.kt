package `in`.ding.user.auth.application.rest

import `in`.ding.common.http.RequestId
import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.request.OtpIssueRequest
import `in`.ding.user.auth.application.dto.request.OtpVerifyRequest
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse
import `in`.ding.user.auth.application.service.OtpIssueService
import `in`.ding.user.auth.application.service.OtpService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val issueService: OtpIssueService,
    val service: OtpService
) {
    @PostMapping("/otp/issue")
    fun issueOtp(
        @RequestBody body: OtpIssueRequest,
        @RequestId requestId: String
    ) {
        issueService.issue(OtpIssueCommand.of(body, requestId))
    }

    @PostMapping("/otp/verify")
    fun verifyOtp(@RequestBody body: OtpVerifyRequest): OtpVerifyResponse {
        return service.verify(OtpVerifyCommand.of(body))
    }

    @GetMapping("/users/{userExKey}/token")
    fun getToken(@PathVariable userExKey: UUID): OtpVerifyResponse {
        return service.issueTokenForTest(userExKey)
    }
}
