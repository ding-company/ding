package `in`.ding.common.infra.security.config

import `in`.ding.common.infra.security.filter.JwtAuthenticationFilter
import `in`.ding.common.infra.security.handler.JwtAccessDeniedHandler
import `in`.ding.common.infra.security.handler.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val accessDeniedHandler: JwtAccessDeniedHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .authorizeHttpRequests {
                it
                    // 완전 공개
                    .requestMatchers(
                        "/api/v1/auth/otp/issue",
                        "/api/v1/auth/otp/verify",
                        "/actuator/health"
                    ).permitAll()
                    // OTP 토큰만 있어도 접근 가능
                    .requestMatchers(
                        "/api/v1/auth/post-auth/status"
                    ).hasAuthority("TOKEN_OTP")
                    // 약관 관련 (OTP or PRE_AUTH)
                    .requestMatchers(
                        "/api/v1/terms/**",
                        "/api/v1/auth/terms/**"
                    ).hasAnyAuthority("TOKEN_OTP", "TOKEN_PRE_AUTH")
                    // 완전 인증 필요
                    .anyRequest()
                    .hasAuthority("TOKEN_AUTHENTICATED")
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}
