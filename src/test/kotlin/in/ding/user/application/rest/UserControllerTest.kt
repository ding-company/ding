package `in`.ding.user.application.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import `in`.ding.common.auth.JwtAuthenticationFilter
import `in`.ding.common.auth.JwtConfig
import `in`.ding.common.auth.JwtTokenProvider
import `in`.ding.common.auth.SecurityConfig
import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.http.UserOtpRequest
import `in`.ding.user.user.application.rest.UserController
import `in`.ding.user.user.application.service.UserAppServiceImpl
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.kotlin.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@Import(SecurityConfig::class, JwtAuthenticationFilter::class, JwtTokenProvider::class, JwtConfig::class)
@WebMvcTest(UserController::class)
class UserControllerTest(
    val mockMvc: MockMvc,
    @MockBean
    val userService: UserAppServiceImpl
) : BehaviorSpec({
    given("회원가입 요청이 주어졌을 때") {
        `when`("유효한 요청이 전달되면") {
            val request = UserOtpRequest(
                "testUser",
                "password123",
                nationality = UserNationality.KR
            )
            doNothing().`when`(userService).register(UserRegisterCommand.of(request))

            val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            )
            then("회원가입이 성공하고 상태코드 201을 반환해야 한다") {
                result
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated)
            }
        }

        `when`("잘못된 요청이 전달되면") {
            // given: 유효하지 않은 요청 JSON
            val invalidRequestJson = """
            {
                "username": "",
                "email": "invalid-email"
            }
            """.trimIndent()
            val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidRequestJson)
            )
            then("회원가입이 실패하고 적절한 에러 메시지를 반환해야 한다") {
                result
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
            }
        }
    }
})
