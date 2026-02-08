package `in`.ding.common.infra.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.ding.common.infra.http.MetaCode
import `in`.ding.common.infra.http.ResponseDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = "application/json;charset=UTF-8"

        val body = ResponseDTO(
            meta = ResponseDTO.Meta(
                code = MetaCode.FORBIDDEN,
                message = accessDeniedException.message ?: "Access denied"
            ),
            data = null
        )

        response.writer.write(objectMapper.writeValueAsString(body))
    }
}
