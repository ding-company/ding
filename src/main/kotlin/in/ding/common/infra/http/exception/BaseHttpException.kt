package `in`.ding.common.infra.http.exception

import `in`.ding.common.infra.http.MetaCode

open class BaseHttpException(
    open val metaCode: MetaCode,
    override val message: String,
    val data: Any? = null
) : RuntimeException()
