package `in`.ding.common.infra.http.exception

import `in`.ding.common.infra.http.MetaCode

class InternalServerException(message: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.INTERNAL_SERVER_ERROR,
    message = message ?: MetaCode.CONFLICT.toString(),
    data = data
)
