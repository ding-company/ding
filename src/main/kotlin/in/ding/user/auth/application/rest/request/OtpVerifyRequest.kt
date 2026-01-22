package `in`.ding.user.auth.application.rest.request

import `in`.ding.user.user.domain.model.enumerate.UserNationality
import io.swagger.v3.oas.annotations.media.Schema

data class OtpVerifyRequest(
    @field:Schema(description = "전화번호 or email", example = "01012341234", minLength = 11, maxLength = 11)
    val contact: String,
    @field:Schema(description = "국적", example = "KR", minLength = 4, maxLength = 20)
    val nationality: UserNationality,
    @field:Schema(description = "otcCode", example = "12311", minLength = 6, maxLength = 6)
    val otpCode: String,
)
