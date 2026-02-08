package `in`.ding.common.infra.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.ding.common.infra.http.MetaCode
import `in`.ding.common.infra.http.ResponseDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"

        val body = ResponseDTO(
            meta = ResponseDTO.Meta(
                code = MetaCode.AUTHENTICATION_FAILED,
                message = "Authentication required"
            ),
            data = null
        )

        response.writer.write(objectMapper.writeValueAsString(body))
    }
}
