package `in`.ding.common.infra.http

import `in`.ding.common.infra.http.exception.BadRequestException
import `in`.ding.common.infra.http.exception.BaseHttpException
import `in`.ding.common.infra.http.exception.ConflictException
import `in`.ding.common.infra.http.exception.InternalServerException
import `in`.ding.common.infra.http.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalControllerExceptionHandler {

//    companion object : Log

    @ExceptionHandler(
        value = [
            (MissingServletRequestParameterException::class),
            (HttpMessageNotReadableException::class),
        ]
    )
    @ResponseBody
    fun handleServletRequest(): ResponseEntity<ResponseDTO<Any>> {
        return createErrorResponse(statusCode = HttpStatus.BAD_REQUEST, message = "parseError")
    }

    @ExceptionHandler(
        value = [
            (BadRequestException::class),
            (ConflictException::class),
            (NotFoundException::class),
            (InternalServerException::class),
        ]
    )
    @ResponseBody
    fun handleBaseHttpException(error: BaseHttpException):
        ResponseEntity<ResponseDTO<Any>> {
        val status = when (error) {
            is BadRequestException -> HttpStatus.BAD_REQUEST
            is ConflictException -> HttpStatus.CONFLICT
            is NotFoundException -> HttpStatus.NOT_FOUND
            is InternalServerException -> HttpStatus.INTERNAL_SERVER_ERROR
            //  TODO internal server error가 아닌 다르것으로 처리해야함
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return createErrorResponse(statusCode = status, message = error.message, data = error.data)
    }

//    @SentryCapture
    @ExceptionHandler(
        value = [
            (Exception::class)
        ]
    )
    fun handleUnhandledException(error: Exception):
        ResponseEntity<ResponseDTO<Any>> {
        return createErrorResponse(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, message = error.message)
    }

//    @SentryCapture
    @ExceptionHandler(
        value = [
            (NotImplementedError::class)
        ]
    )
    fun handleNotImplementedError(error: NotImplementedError):
        ResponseEntity<ResponseDTO<Any>> {
        return createErrorResponse(statusCode = HttpStatus.NOT_IMPLEMENTED, message = error.message)
    }

//    @SentryCapture
//    @ExceptionHandler(
//        value = [
//            (RemoteCallException::class)
//        ]
//    )
//    fun handleRemoteCallException(error: RemoteCallException):
//            ResponseEntity<ResponseDTO<Any>> {
//        val status = error.remoteStatus
//
//        return createErrorResponse(statusCode = status, message = error.message)
//    }
//
//    @SentryCapture
//    @ExceptionHandler(
//        value = [
//            (UnprocessableException::class)
//        ]
//    )
//    fun handleUnprocessableError(error: UnprocessableException):
//            ResponseEntity<ResponseDTO<Any>> {
//        return createErrorResponse(statusCode = HttpStatus.UNPROCESSABLE_ENTITY, message = error.message)
//    }

    fun createErrorResponse(statusCode: HttpStatus, message: String? = null, data: Any? = null):
        ResponseEntity<ResponseDTO<Any>> {
        val dtoMetaCode = MetaCode.valueFrom(statusCode)

        val dto = ResponseDTO(
            meta = ResponseDTO.Meta(
                code = dtoMetaCode,
                type = dtoMetaCode.name.lowercase(),
                message = message
            ),
            data = data
        )

        return ResponseEntity(dto, statusCode)
    }
}
