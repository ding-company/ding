package `in`.ding.user.auth.domain.refresh

import `in`.ding.common.infra.security.jwt.exception.InvalidTokenException
import `in`.ding.common.infra.security.jwt.exception.TokenExpiredException
import `in`.ding.user.auth.domain.model.RefreshToken
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RefreshTokenValidator {

    fun validate(token: RefreshToken) {
        if (token.isUsed) {
            throw InvalidTokenException()
        }

        if (token.expiresAt.isBefore(LocalDateTime.now())) {
            throw TokenExpiredException()
        }

        token.absoluteExpiresAt?.let {
            if (it.isBefore(LocalDateTime.now())) {
                throw TokenExpiredException()
            }
        }
    }
}
