package `in`.ding.user.application.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import `in`.ding.common.auth.AuthUser
import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.http.RegisterUserRequest
import `in`.ding.user.user.application.rest.UserController
import `in`.ding.user.user.application.service.UserAppServiceImpl
import io.kotest.core.spec.style.BehaviorSpec
import org.mockito.Mockito.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@WebMvcTest(UserController::class)
@ActiveProfiles("test")
class UserControllerTest(
    val mockMvc: MockMvc,
    @MockBean
    val userService: UserAppServiceImpl
) : BehaviorSpec({
    given("회원가입 요청이 주어졌을 때") {
        val authUser = AuthUser(UUID.randomUUID())
        val authentication = UsernamePasswordAuthenticationToken(authUser, null, emptyList())

        `when`("유효한 요청이 전달되면") {
            val request = RegisterUserRequest(
                termsAgreement =
                listOf()
            )
            doNothing().`when`(userService).register(UserRegisterCommand.of(request, authUser.exKey))

            val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
                    .with(
                        SecurityMockMvcRequestPostProcessors.authentication(authentication)
                    ).with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            then("회원가입이 성공하고 상태코드 200을 반환해야 한다") {
                result
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk)
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
                MockMvcRequestBuilders.post("/api/v1/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidRequestJson)
                    .with(
                        SecurityMockMvcRequestPostProcessors.authentication(authentication)
                    ).with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            then("회원가입이 실패하고 적절한 에러 메시지를 반환해야 한다") {
                result
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
            }
        }
    }
})
