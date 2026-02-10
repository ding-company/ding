package `in`.ding.common.infra.security.filter

import `in`.ding.common.infra.security.jwt.JwtTokenProvider
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
const val TOKEN_START_INDEX = 7

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    @Suppress("SwallowedException")
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolve(request)

        if (token != null) {
            try {
                val payload = jwtTokenProvider.parse(token)

                SecurityContextHolder.getContext().authentication =
                    jwtTokenProvider.toAuthentication(payload)
            } catch (e: JwtException) {
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolve(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(TOKEN_START_INDEX)
}
