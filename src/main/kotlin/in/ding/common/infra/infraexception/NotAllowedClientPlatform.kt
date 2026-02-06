package `in`.ding.common.infra.infraexception

import `in`.ding.common.infra.http.MetaCode
import `in`.ding.common.infra.http.exception.BaseHttpException

class NotAllowedClientPlatform(data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.BAD_REQUEST,
    message = ErrorMessage.INVALID_CLIENT_PLATFORM,
    data = data
)
