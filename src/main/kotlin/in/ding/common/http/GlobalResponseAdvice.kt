package `in`.ding.common.http

import `in`.ding.common.MetaCode
import `in`.ding.common.ResponseDTO
import `in`.ding.common.exception.BadRequestException
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

// ("throw 처리")
@ControllerAdvice
class GlobalResponseAdvice : ResponseBodyAdvice<Any> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true // 모든 응답에 적용
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): ResponseDTO<*>? {
        if (body is ResponseDTO<*>) {
            return body
        }
        val servletResponse = (response as? ServletServerHttpResponse)?.servletResponse
        val statusCode = servletResponse?.status ?: HttpStatus.OK
        val result = when (statusCode) {
            HttpStatus.OK.value() -> {
                ResponseDTO(data = body, meta = ResponseDTO.Meta(MetaCode.SUCCESS))
            }
            HttpStatus.CREATED.value() -> {
                ResponseDTO(data = body, meta = ResponseDTO.Meta(MetaCode.CREATED))
            }
            HttpStatus.ACCEPTED.value() -> {
                ResponseDTO(data = body, meta = ResponseDTO.Meta(MetaCode.ACCEPTED))
            }
            HttpStatus.NO_CONTENT.value() -> {
                null
            }
            else -> throw BadRequestException()
        }
        return result
    }
}
