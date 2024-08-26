package `in`.ding.user.domain.service.dto

import `in`.ding.user.application.dto.enumerate.UserSignupRequest

class UserRegisterDTO(
    val phoneNumber: String?,
    val email: String?,
    val name: String?,
) {
    companion object {
        fun of(request: UserSignupRequest): UserRegisterDTO {
            return UserRegisterDTO(phoneNumber = request.phoneNumber, email = request.email, name = request.name)
        }
    }
}
