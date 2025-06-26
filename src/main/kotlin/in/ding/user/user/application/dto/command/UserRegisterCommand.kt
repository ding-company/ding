package `in`.ding.user.user.application.dto.command

import `in`.ding.user.user.application.dto.http.UserOtpRequest
import `in`.ding.user.user.domain.model.enumerate.UserNationality

class UserRegisterCommand(
    val phoneNumber: String?,
    val email: String?,
    val nationality: UserNationality,
) {
    companion object {
        fun of(request: UserOtpRequest): UserRegisterCommand {
            return UserRegisterCommand(
                phoneNumber = request.phoneNumber,
                email = request.email,
                nationality = request.nationality
            )
        }
    }
}
