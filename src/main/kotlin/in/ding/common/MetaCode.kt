package `in`.ding.common

import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.http.HttpStatus

// TODO 공부
enum class MetaCode(private var code: String) {
    SUCCESS("20000000"),
    CREATED("20100000"),
    ACCEPTED("20200000"),
    NO_CONTENT("20400000"),
    BAD_REQUEST("40000000"),
    PARSE_ERROR("40000001"),
    BULK_CREATE_ERROR("40000002"),
    INVALID_CHARACTER("40000003"),
    UNLINK_NAVER_BUSINESS_WHEN_CHANGING_POS_MODE("40001000"),
    AUTHENTICATION_FAILED("40100000"),
    NOT_AUTHENTICATED("40100001"),
    EXPIRED_AUTHENTICATION("40100002"),
    FORBIDDEN("40300000"),
    PERMISSION_DENIED("40300001"),
    NOT_FOUND("40400000"),
    METHOD_NOW_ALLOWED("40500000"),
    NOT_ACCEPTABLE("40600000"),
    CONFLICT("40900000"),
    UNSUPPORTED_MEDIA_TYPE("41500000"),
    UNPROCESSABLE_ENTITY("42200000"),
    THROTTLED("42900000"),
    INTERNAL_SERVER_ERROR("50000000");

    @JsonValue
    override fun toString(): String {
        return this.code
    }
    companion object {
        @Suppress("CyclomaticComplexMethod")
        fun valueFrom(status: HttpStatus) = when (status) {
            HttpStatus.OK -> SUCCESS
            HttpStatus.CREATED -> CREATED
            HttpStatus.ACCEPTED -> ACCEPTED
            HttpStatus.NO_CONTENT -> NO_CONTENT
            HttpStatus.BAD_REQUEST -> BAD_REQUEST
            HttpStatus.UNAUTHORIZED -> AUTHENTICATION_FAILED
            HttpStatus.FORBIDDEN -> FORBIDDEN
            HttpStatus.NOT_FOUND -> NOT_FOUND
            HttpStatus.METHOD_NOT_ALLOWED -> METHOD_NOW_ALLOWED
            HttpStatus.NOT_ACCEPTABLE -> NOT_ACCEPTABLE
            HttpStatus.UNSUPPORTED_MEDIA_TYPE -> UNSUPPORTED_MEDIA_TYPE
            HttpStatus.CONFLICT -> CONFLICT
            HttpStatus.INTERNAL_SERVER_ERROR -> INTERNAL_SERVER_ERROR
            HttpStatus.UNPROCESSABLE_ENTITY -> UNPROCESSABLE_ENTITY
            else -> throw IllegalArgumentException("Please provide correct status.")
        }
    }
}
