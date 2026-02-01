package `in`.ding.common.infra.http.exception

import `in`.ding.common.infra.http.MetaCode

open class NotFoundException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.NOT_FOUND,
    message = message ?: MetaCode.NOT_FOUND.toString(),
    data = data
)
