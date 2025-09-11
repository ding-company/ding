package `in`.ding.common.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) { @Bean
fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
    return JwtAuthenticationFilter(jwtTokenProvider)
}

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/api/v1/auth/otp/issue",
                        "/api/v1/auth/otp/verify",
                        "/actuator/health",
                        "/api/v1/auth/users/*/token"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
