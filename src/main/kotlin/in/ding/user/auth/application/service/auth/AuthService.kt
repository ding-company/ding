package `in`.ding.user.auth.application.service.auth

import `in`.ding.common.infra.http.ClientPlatform
import `in`.ding.common.infra.security.jwt.TokenIssuer
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.rest.response.PostAuthStatus
import `in`.ding.user.auth.application.rest.response.PostAuthStatusResponse
import `in`.ding.user.auth.domain.exception.VerifiedIdentityNotFound
import `in`.ding.user.auth.domain.repository.RedisVerifiedIdentityRepository
import `in`.ding.user.term.application.service.TermQueryService
import `in`.ding.user.term.domain.model.enumerate.ServiceChannel
import `in`.ding.user.user.domain.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val tokenIssuer: TokenIssuer,
    private val verifiedIdentityRepository: RedisVerifiedIdentityRepository,
    private val termQueryService: TermQueryService,
    private val userRepository: UserRepository
) {
    fun getAuthStatus(query: GetPostAuthStatusQuery): PostAuthStatusResponse {
        verifiedIdentityRepository.findVerifiedIdentity(query.userExKey) ?: throw VerifiedIdentityNotFound()
        val user = userRepository.findByExKey(query.userExKey)
        val serviceChannel = when (query.clientPlatform) {
            ClientPlatform.ANDROID -> ServiceChannel.ANDROID
            ClientPlatform.IOS -> ServiceChannel.IOS
            else -> null
        }
        var result: PostAuthStatusResponse
        if (user == null) {
            result = PostAuthStatusResponse(PostAuthStatus.NOT_REGISTERED)
        } else {
            if (termQueryService.isAllRequiredAgreed(
                    query.userExKey,
                    query.principalType,
                    serviceChannel,
                    query.nationality
                )
            ) {
                result = PostAuthStatusResponse(PostAuthStatus.REQUIRE_TERMS)
            } else {
                result = PostAuthStatusResponse(PostAuthStatus.AUTHENTICATED)
            }
        }
        return result
    }
    fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse(tokenIssuer.issueOtpToken(userExKey))
    }
}
