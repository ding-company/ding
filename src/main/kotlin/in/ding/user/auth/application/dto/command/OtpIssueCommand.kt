package `in`.ding.user.auth.application.dto.command

import `in`.ding.user.auth.application.dto.request.OtpIssueRequest
import `in`.ding.user.user.domain.model.enumerate.UserNationality

data class OtpIssueCommand(
    val phoneNumber: String?,
    val email: String?,
    val nationality: UserNationality,
    val requestId: String
) {
    companion object {
        fun of(request: OtpIssueRequest, requestId: String,): OtpIssueCommand {
            return OtpIssueCommand(
                phoneNumber = request.phoneNumber,
                email = request.email,
                nationality = request.nationality,
                requestId = requestId
            )
        } }
}
