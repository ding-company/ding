package `in`.ding
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            // Enables recognition of java.time.LocalDateTime
            .registerModule(JavaTimeModule())
            // Maps camelCase to snake_case field names
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            // Ensures dates are serialized as ISO strings, not numeric arrays
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}
