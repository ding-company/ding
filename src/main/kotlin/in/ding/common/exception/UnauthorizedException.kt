package `in`.ding.common.exception

import `in`.ding.common.MetaCode

open class UnauthorizedException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.AUTHENTICATION_FAILED,
    message = message ?: "AUTHENTICATION FAILED",
    data = data
)
