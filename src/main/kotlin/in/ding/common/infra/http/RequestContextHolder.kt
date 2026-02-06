package `in`.ding.common.infra.http

@Suppress("UseCheckOrError")
object RequestContextHolder {
    private val contextHolder = ThreadLocal<RequestContext>()

    fun set(context: RequestContext) {
        contextHolder.set(context)
    }

    // TODO 변경
    fun get(): RequestContext =
        contextHolder.get()
            ?: throw IllegalStateException("RequestContext not initialized")

    fun clear() {
        contextHolder.remove()
    }
}
