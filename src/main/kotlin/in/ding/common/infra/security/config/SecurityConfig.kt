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
                    .requestMatchers(
                        "/api/v1/auth/otp/issue",
                        "/api/v1/auth/otp/verify",
                        "/api/v1/auth/post-auth/status",
                        "/api/v1/auth/terms/**",
                        "/api/v1/terms/**",
                        "/actuator/health"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}
