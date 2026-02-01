package `in`.ding.common.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.user.domain.model.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    private val objectMapper: ObjectMapper
) {
    private fun <T> createTemplate(
        connectionFactory: RedisConnectionFactory,
        type: Class<T>
    ): RedisTemplate<String, T> {
        val template = RedisTemplate<String, T>()
        template.setConnectionFactory(connectionFactory)

        val serializer = Jackson2JsonRedisSerializer(objectMapper, type)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = serializer

        return template
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        return createTemplate(connectionFactory, Any::class.java)
    }

    @Bean
    fun otpSessionRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, OtpSession> {
        return createTemplate(connectionFactory, OtpSession::class.java)
    }

    @Bean
    fun userRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, User> {
        return createTemplate(connectionFactory, User::class.java)
    }
}
