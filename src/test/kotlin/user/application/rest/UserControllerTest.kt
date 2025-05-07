package user.application.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import `in`.ding.user.application.dto.enumerate.UserSignupRequest
import `in`.ding.user.application.rest.UserController
import `in`.ding.user.application.service.UserAppService
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(UserController::class)
@ActiveProfiles("test")
class UserControllerTest(
    val mockMvc: MockMvc,
    @MockBean
    val userService: UserAppService
) : BehaviorSpec({
    given("회원가입 요청이 주어졌을 때") {
        every { userService.register(any()) } returns Unit
        `when`("유효한 요청이 전달되면") {
            val request = UserSignupRequest("testUser", "password123", "test@example.com", "123111")

            val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            )
            then("회원가입이 성공하고 상태코드 201을 반환해야 한다") {
                result
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated)
                    .andExpect(MockMvcResultMatchers.content().string("회원가입 성공"))
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
                    .andExpect(MockMvcResultMatchers.content().string("회원가입 실패"))
            }
        }
    }
})
