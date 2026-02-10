package `in`.ding.user.auth.domain.repository

import `in`.ding.user.auth.domain.model.RefreshToken
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface RefreshTokenRepository {

    fun save(token: RefreshToken)

    fun find(token: String): RefreshToken?

    fun markUsed(token: String)

    fun revokeAllBySubject(subject: UUID)
}
