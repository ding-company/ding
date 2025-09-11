package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse
import java.util.UUID

interface OtpService {
    fun issue(command: OtpIssueCommand)
    fun verify(command: OtpVerifyCommand): OtpVerifyResponse
    fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse
}
