package `in`.ding.common.infra.security

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.ding.common.infra.http.MetaCode
import `in`.ding.common.infra.http.ResponseDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val dto = ResponseDTO(
            meta = ResponseDTO.Meta(
                code = MetaCode.AUTHENTICATION_FAILED,
                message = authException.message ?: "AUTHENTICATION FAILED"
            ),
            data = null
        )

        response.writer.write(ObjectMapper().writeValueAsString(dto))
    }
}
