package `in`.ding.common.redis

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
    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        return template
    }

    @Bean
    fun otpSessionRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, OtpSession> {
        val template = RedisTemplate<String, OtpSession>()
        template.setConnectionFactory(connectionFactory)

        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(objectMapper, OtpSession::class.java)

        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = jackson2JsonRedisSerializer
        return template
    }

    @Bean
    fun userRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, User> {
        val template = RedisTemplate<String, User>()
        template.setConnectionFactory(connectionFactory)

        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(objectMapper, User::class.java)

        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = jackson2JsonRedisSerializer
        return template
    }
}
