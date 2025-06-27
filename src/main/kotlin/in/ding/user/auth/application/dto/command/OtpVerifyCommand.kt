package `in`.ding.user.auth.application.dto.command

import `in`.ding.user.auth.application.dto.request.OtpVerifyRequest
import `in`.ding.user.user.domain.model.enumerate.UserNationality

data class OtpVerifyCommand(
    val phoneNumber: String?,
    val email: String?,
    val nationality: UserNationality,
) {
    companion object {
        fun of(request: OtpVerifyRequest): OtpVerifyCommand {
            return OtpVerifyCommand(
                phoneNumber = request.phoneNumber,
                email = request.email,
                nationality = request.nationality,
            )
        } }
}
