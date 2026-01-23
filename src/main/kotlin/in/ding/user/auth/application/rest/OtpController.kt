package `in`.ding.user.auth.application.rest

import `in`.ding.common.http.RequestId
import `in`.ding.user.auth.application.rest.request.OtpIssueRequest
import `in`.ding.user.auth.application.rest.request.OtpVerifyRequest
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.service.issue.OtpIssueCommand
import `in`.ding.user.auth.application.service.issue.OtpIssueService
import `in`.ding.user.auth.application.service.verify.OtpVerifyCommand
import `in`.ding.user.auth.application.service.verify.OtpVerifyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth/otp")
class OtpController(
    private val issueService: OtpIssueService,
    private val verifyService: OtpVerifyService
) {
    @PostMapping("/issue")
    fun issueOtp(
        @RequestBody body: OtpIssueRequest,
        @RequestId requestId: String
    ) {
        issueService.issue(OtpIssueCommand.of(body, requestId))
    }

    @PostMapping("/verify")
    fun verifyOtp(@RequestBody body: OtpVerifyRequest): OtpVerifyResponse {
        return verifyService.verify(OtpVerifyCommand.of(body))
    }
}
