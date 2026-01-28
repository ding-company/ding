package `in`.ding.user.auth.application.rest.request

import `in`.ding.user.domain.enumerate.UserNationality
import io.swagger.v3.oas.annotations.media.Schema

data class OtpIssueRequest(
    @field:Schema(description = "전화번호 or email", example = "01012341234", minLength = 11, maxLength = 16)
    val contact: String,
    @field:Schema(description = "국적", example = "KR", minLength = 2, maxLength = 20)
    val nationality: UserNationality,
)
