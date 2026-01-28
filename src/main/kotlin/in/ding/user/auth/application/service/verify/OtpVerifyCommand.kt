package `in`.ding.user.auth.application.service.verify

import `in`.ding.user.auth.application.rest.request.OtpVerifyRequest
import `in`.ding.user.domain.enumerate.UserNationality

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
