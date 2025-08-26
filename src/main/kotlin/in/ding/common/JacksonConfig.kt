package `in`.ding.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomLocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    override fun serialize(
        value: LocalDateTime?,
        gen: JsonGenerator?,
        serializers: SerializerProvider?
    ) {
        if (value != null) {
            gen?.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
        }
    }
}

class CustomLocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDateTime? {
        val dateAsString = p?.text
        return if (dateAsString != null) {
            LocalDateTime.parse(dateAsString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        } else {
            null
        }
    }
}

@Configuration
class JacksonConfig {
    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.modulesToInstall(JavaTimeModule())
        }
    }
}
