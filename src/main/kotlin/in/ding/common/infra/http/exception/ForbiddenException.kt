package `in`.ding.common.infra.http.exception

import `in`.ding.common.infra.http.MetaCode

open class ForbiddenException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.FORBIDDEN,
    message = message ?: MetaCode.FORBIDDEN.toString(),
    data = data
)
