package `in`.ding.user.auth.application.rest

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
    fun issueOtp(@RequestBody request: OtpIssueRequest) {
        service.issue(OtpIssueCommand.of(request))
    }

    @PostMapping("/otp/verify")
    fun verifyOtp(@RequestBody request: OtpVerifyRequest) {
        service.verify(OtpVerifyCommand.of(request))
    }
}
