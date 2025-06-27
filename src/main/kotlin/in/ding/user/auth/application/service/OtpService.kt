package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import org.springframework.stereotype.Service

@Service
interface OtpService {
    fun issue(command: OtpIssueCommand)
    fun verify(command: OtpVerifyCommand)
}
