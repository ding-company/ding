package `in`.ding.user.domain.service.dto

import `in`.ding.user.application.dto.enumerate.UserSignupRequest
import `in`.ding.user.domain.model.enumerate.UserNationality

class UserRegisterDTO(
    val phoneNumber: String?,
    val email: String?,
    val name: String?,
    val nationality: UserNationality,
) {
    companion object {
        fun of(request: UserSignupRequest): UserRegisterDTO {
            return UserRegisterDTO(
                phoneNumber = request.phoneNumber,
                email = request.email,
                name = request.name,
                nationality = request.nationality
            )
        }
    }
}
