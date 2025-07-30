package `in`.ding.common.exception

import `in`.ding.common.MetaCode

open class ForbiddenException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.FORBIDDEN,
    message = message ?: MetaCode.FORBIDDEN.toString(),
    data = data
)
