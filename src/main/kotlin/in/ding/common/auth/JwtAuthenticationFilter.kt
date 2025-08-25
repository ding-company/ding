package `in`.ding.common.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val exceptionUri = arrayOf("/api/v1/auth/otp/issue", "/api/v1/auth/otp/verify", "/actuator/health")
        if (request.requestURI in exceptionUri) {
            filterChain.doFilter(request, response)
            return
        }
        val token = resolveToken(request)

        if (token != null && jwtTokenProvider.validateToken(token)) {
            val userExKey = jwtTokenProvider.extractUserExKey(token)
            val authUser = AuthUser(userExKey)

            val authentication = UsernamePasswordAuthenticationToken(authUser, null, emptyList())
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Unauthorized: JWT token is invalid or expired")
            return
        }

        filterChain.doFilter(request, response)
    }

    @Suppress("MagicNumber")
    private fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization")
        return if (header != null && header.startsWith("Bearer ")) header.substring(7) else null
    }
}
