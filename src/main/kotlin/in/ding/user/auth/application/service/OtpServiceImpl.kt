package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import org.springframework.stereotype.Service

@Service
class OtpServiceImpl : OtpService {
    override fun issue(command: OtpIssueCommand): String {
        return ""
    }
    override fun verify(command: OtpVerifyCommand) {
        TODO()
    }
}
