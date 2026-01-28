package `in`.ding.customer.application.dto.http

import `in`.ding.user.domain.enumerate.UserNationality
import io.swagger.v3.oas.annotations.media.Schema

data class PostCustomerRequest(
    @field:Schema(description = "전화번호", example = "01012341234", minLength = 11, maxLength = 11)
    val phoneNumber: String?,
    @field:Schema(description = "이메일", example = "kljadsg@magmkla.com", minLength = 5, maxLength = 50)
    val email: String?,
    @field:Schema(description = "사용자의 이름", example = "홍길동", minLength = 2, maxLength = 30)
    val name: String?,
    @field:Schema(description = "비밀번호", example = "9128391", minLength = 4, maxLength = 20)
    val password: String,
    @field:Schema(description = "국적", example = "KR", minLength = 4, maxLength = 20)
    val nationality: UserNationality,
)
