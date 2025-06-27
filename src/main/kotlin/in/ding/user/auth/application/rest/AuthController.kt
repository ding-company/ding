package `in`.ding.user.auth.application.rest

import `in`.ding.common.http.RequestId
import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.request.OtpIssueRequest
import `in`.ding.user.auth.application.dto.request.OtpVerifyRequest
import `in`.ding.user.auth.application.service.OtpService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val service: OtpService) {
    @PostMapping("/otp/issue")
    fun issueOtp(
        @RequestBody body: OtpIssueRequest,
        @RequestId requestId: String
    ) {
        service.issue(OtpIssueCommand.of(body, requestId))
    }

    @PostMapping("/otp/verify")
    fun verifyOtp(@RequestBody body: OtpVerifyRequest) {
        service.verify(OtpVerifyCommand.of(body))
    }
}
