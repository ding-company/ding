package `in`.ding.user.user.application.dto.http

import `in`.ding.user.user.domain.model.enumerate.UserNationality
import io.swagger.v3.oas.annotations.media.Schema

class UserOtpRequest(
    @field:Schema(description = "전화번호", example = "01012341234", minLength = 11, maxLength = 11)
    val phoneNumber: String?,
    @field:Schema(description = "이메일", example = "kljadsg@magmkla.com", minLength = 5, maxLength = 50)
    val email: String?,
    @field:Schema(description = "국적", example = "KR", minLength = 4, maxLength = 20)
    val nationality: UserNationality,
)
