package `in`.ding.common.infra.infraexception

import `in`.ding.common.infra.http.MetaCode
import `in`.ding.common.infra.http.exception.BaseHttpException

class MissedHeaderException(headerName: String? = null, data: Any? = null) : BaseHttpException(
    metaCode = MetaCode.BAD_REQUEST,
    message = "$headerName 이 누락 되었습니다.",
    data = data
)
