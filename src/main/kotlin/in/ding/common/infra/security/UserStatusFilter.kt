package `in`.ding.common.infra.security

import `in`.ding.common.infra.http.exception.UnauthorizedException
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class UserStatusFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.isAuthenticated) {
            val principal = authentication.principal

            if (principal is AuthUser) {
                if (principal.status == UserStatus.TEMPORARY &&
                    !isTemporaryAllowedEndpoint(request) &&
                    !isUnCheckedUrl(request)
                ) {
                    throw UnauthorizedException()
                }
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun isTemporaryAllowedEndpoint(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        return uri.startsWith("/api/v1/terms") ||
            uri.startsWith("/api/v1/auth/terms") ||
            // TODO 테스트 코드 수정후 제거
            uri.equals("/api/v1/users/register")
    }

    private fun isUnCheckedUrl(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        return uri.equals("/api/v1/auth/otp/issue") ||
            uri.equals("/api/v1/auth/otp/verify") ||
            uri.equals("/actuator/health") ||
            uri.equals("/api/v1/auth/users/*/token")
    }
}
