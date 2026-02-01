package `in`.ding.common.infra.http.exception

import `in`.ding.common.infra.http.MetaCode

open class ConflictException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.CONFLICT,
    message = message ?: MetaCode.CONFLICT.toString(),
    data = data
)
