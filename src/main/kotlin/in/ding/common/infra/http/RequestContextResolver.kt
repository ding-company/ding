package `in`.ding.common.infra.http

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class RequestContextResolver(
    private val clientPlatformResolver: ClientPlatformResolver,
) {

    fun resolve(request: HttpServletRequest): RequestContext {
        val clientPlatform = clientPlatformResolver.resolve(request)

        return RequestContext(
            clientPlatform = clientPlatform,
        )
    }
}
