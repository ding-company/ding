package `in`.ding.common.exception

import `in`.ding.common.MetaCode

class InternalServerException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.INTERNAL_SERVER_ERROR,
    message = message ?: MetaCode.CONFLICT.toString(),
    data = data
)
