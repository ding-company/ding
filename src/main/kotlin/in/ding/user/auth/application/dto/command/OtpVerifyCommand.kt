package `in`.ding.user.auth.application.dto.command

import `in`.ding.user.auth.application.dto.request.OtpVerifyRequest
import `in`.ding.user.user.domain.model.enumerate.UserNationality

data class OtpVerifyCommand(
    val contact: String,
    val nationality: UserNationality,
    val otpCode: String,
) {
    companion object {
        fun of(request: OtpVerifyRequest): OtpVerifyCommand {
            return OtpVerifyCommand(
                contact = request.contact,
                nationality = request.nationality,
                otpCode = request.otpCode
            )
        } }
}
