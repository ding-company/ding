package `in`.ding.common.exception

import `in`.ding.common.MetaCode

open class BaseHttpException(
    open val metaCode: MetaCode,
    override val message: String,
    val data: Any? = null
) : RuntimeException()
