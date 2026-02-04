package `in`.ding.common.infra.http

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.text.clear
import kotlin.text.set

@Component
class RequestContextFilter(
    private val requestContextResolver: RequestContextResolver
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val context = requestContextResolver.resolve(request)
            RequestContextHolder.set(context)
            filterChain.doFilter(request, response)
        } finally {
            RequestContextHolder.clear()
        }
    }
}
