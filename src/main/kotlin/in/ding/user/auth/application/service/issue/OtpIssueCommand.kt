package `in`.ding.user.auth.application.service.issue

import `in`.ding.user.auth.application.rest.request.OtpIssueRequest
import `in`.ding.user.domain.enumerate.UserNationality

data class OtpIssueCommand(
    val contact: String,
    val nationality: UserNationality,
    val requestId: String
) {
    companion object {
        fun of(request: OtpIssueRequest, requestId: String,): OtpIssueCommand {
            return OtpIssueCommand(
                contact = request.contact,
                nationality = request.nationality,
                requestId = requestId
            )
        } }
}
