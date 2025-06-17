package `in`.ding.user.application.dto.command

import `in`.ding.user.application.dto.http.UserSignupRequest
import `in`.ding.user.domain.model.enumerate.UserNationality

class UserRegisterCommand(
    val phoneNumber: String?,
    val email: String?,
    val name: String?,
    val nationality: UserNationality,
) {
    companion object {
        fun of(request: UserSignupRequest): UserRegisterCommand {
            return UserRegisterCommand(
                phoneNumber = request.phoneNumber,
                email = request.email,
                name = request.name,
                nationality = request.nationality
            )
        }
    }
}
