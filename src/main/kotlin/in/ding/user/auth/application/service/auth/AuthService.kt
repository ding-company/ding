package `in`.ding.user.auth.application.service.auth

import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.rest.response.PostAuthStatus
import `in`.ding.user.auth.application.rest.response.PostAuthStatusResponse
import `in`.ding.user.auth.domain.repository.RedisVerifiedIdentityRepository
import `in`.ding.user.auth.domain.service.TokenIssuer
import `in`.ding.user.term.application.service.TermQueryService
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val tokenIssuer: TokenIssuer,
    private val verifiedIdentityRepository: RedisVerifiedIdentityRepository,
    private val termQueryService: TermQueryService,
    private val userRepository: UserRepository
) {
    fun getAuthStatus(userExKey: UUID): PostAuthStatusResponse {
//        val user = userRepository.findByExKey(userExKey)
//        val verifiedIdentity = verifiedIdentityRepository.findVerifiedIdentity(userExKey)
//        if (user == null) {
//            return PostAuthStatusResponse(PostAuthStatus.NOT_REGISTERED)
//        } else {
//            if (termQueryService.isAllRequiredAgreed(userExKey)) {}
//        }
        return PostAuthStatusResponse(PostAuthStatus.AUTHENTICATED)
    }
    fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        return OtpVerifyResponse.Companion.of(tokenIssuer.issueTokens(userExKey, UserStatus.REGISTERED))
    }
}
