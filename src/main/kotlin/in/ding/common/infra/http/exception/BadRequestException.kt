package `in`.ding.common.infra.http.exception

import `in`.ding.common.infra.http.MetaCode

open class BadRequestException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.BAD_REQUEST,
    message = message ?: MetaCode.BAD_REQUEST.toString(),
    data = data
)
