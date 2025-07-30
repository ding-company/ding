package `in`.ding.common.exception

import `in`.ding.common.MetaCode

class ConflictException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.CONFLICT,
    message = message ?: MetaCode.CONFLICT.toString(),
    data = data
)
