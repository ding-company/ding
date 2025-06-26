package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand

interface OtpService {
    fun issue(command: OtpIssueCommand): String
    fun verify(command: OtpVerifyCommand)
}
