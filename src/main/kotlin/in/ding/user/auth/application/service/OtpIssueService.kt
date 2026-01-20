package `in`.ding.user.auth.application.service

import `in`.ding.user.auth.application.dto.command.OtpIssueCommand

interface OtpIssueService {
    fun issue(command: OtpIssueCommand)
}
