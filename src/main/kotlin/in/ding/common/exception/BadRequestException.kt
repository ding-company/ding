package `in`.ding.common.exception

import `in`.ding.common.MetaCode

class BadRequestException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.BAD_REQUEST,
    message = message ?: "BAD REQUEST",
    data = data
)
