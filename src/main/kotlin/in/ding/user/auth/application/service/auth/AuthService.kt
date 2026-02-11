package `in`.ding.user.auth.application.service.auth

import `in`.ding.common.infra.http.ClientPlatform
import `in`.ding.common.infra.security.jwt.TokenIssuer
import `in`.ding.common.infra.security.jwt.model.BaseTokenResponse
import `in`.ding.common.infra.security.jwt.model.TokenType
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
    private val userRepository: UserRepository,
) {
    // TODO 전체 리펙토링
    fun getAuthStatus(query: GetPostAuthStatusQuery): PostAuthStatusResponse {
        verifiedIdentityRepository.findVerifiedIdentity(query.verifiedIdentityExKey) ?: throw VerifiedIdentityNotFound()
        val user = userRepository.findByExKey(query.verifiedIdentityExKey)
        val serviceChannel = when (query.clientPlatform) {
            ClientPlatform.ANDROID -> ServiceChannel.ANDROID
            ClientPlatform.IOS -> ServiceChannel.IOS
            else -> null
        }
        var status: PostAuthStatus
        if (user == null) {
            status = PostAuthStatus.NOT_REGISTERED
        } else {
            if (termQueryService.isAllRequiredAgreed(
                    query.verifiedIdentityExKey,
                    query.principalType,
                    serviceChannel,
                    query.nationality
                )
            ) {
                status = PostAuthStatus.REQUIRE_TERMS
            } else {
                status = PostAuthStatus.AUTHENTICATED
            }
        }
        val token = tokenIssuer.issueAuthenticationToken(
            query.verifiedIdentityExKey,
            TokenType.PRE_AUTH

        )
        return PostAuthStatusResponse(status, BaseTokenResponse(token.token, null, token.expiresInSeconds))
    }
    fun issueTokenForTest(userExKey: UUID): OtpVerifyResponse {
        val token = tokenIssuer.issueAuthenticationToken(userExKey, tokenType = TokenType.OTP)
        return OtpVerifyResponse(
            BaseTokenResponse(
                token.token,
                null,
                token.expiresInSeconds
            )
        )
    }
}
