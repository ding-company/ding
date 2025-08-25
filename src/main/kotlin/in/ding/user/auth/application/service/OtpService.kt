package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.application.dto.response.OtpVerifyResponse

interface OtpService {
    fun issue(command: OtpIssueCommand)
    fun verify(command: OtpVerifyCommand): OtpVerifyResponse
}
