package `in`.ding.common.exception

import `in`.ding.common.MetaCode

class NotFoundException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.NOT_FOUND,
    message = message ?: "NOT FOUND",
    data = data
)
