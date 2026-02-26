package `in`.ding.user.auth.application.service.auth

import `in`.ding.common.infra.http.ClientPlatform
import `in`.ding.common.infra.security.jwt.TokenIssuer
import `in`.ding.common.infra.security.jwt.model.BaseTokenResponse
import `in`.ding.common.infra.security.jwt.model.TokenType
import `in`.ding.user.auth.application.rest.response.OtpVerifyResponse
import `in`.ding.user.auth.application.rest.response.PostAuthStatus
import `in`.ding.user.auth.application.rest.response.PostAuthStatusResponse
import `in`.ding.user.auth.domain.exception.NotFoundVerifiedIdentityException
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
    fun getAuthStatus(query: GetPostAuthStatusQuery): PostAuthStatusResponse {
        val verifiedIdentity =
            verifiedIdentityRepository.findVerifiedIdentity(query.verifiedIdentityExKey)
                ?: throw NotFoundVerifiedIdentityException()

        val user =
            userRepository.findByExKey(query.verifiedIdentityExKey)

        val serviceChannel = when (query.clientPlatform) {
            ClientPlatform.ANDROID -> ServiceChannel.ANDROID
            ClientPlatform.IOS -> ServiceChannel.IOS
            else -> null
        }

        val status =
            when {
                user == null ->
                    PostAuthStatus.NOT_REGISTERED

                termQueryService.isAllRequiredAgreed(
                    userExKey = user.exKey,
                    principalType = query.principalType,
                    serviceChannel = serviceChannel,
                    nationality = query.nationality
                ) ->
                    PostAuthStatus.AUTHENTICATED

                else ->
                    PostAuthStatus.REQUIRE_TERMS
            }

        return when (status) {
            PostAuthStatus.NOT_REGISTERED,
            PostAuthStatus.REQUIRE_TERMS -> {
                val token = tokenIssuer.issueAuthenticationToken(
                    subject = verifiedIdentity.exKey,
                    tokenType = TokenType.PRE_AUTH
                )
                PostAuthStatusResponse(
                    status,
                    BaseTokenResponse(token.token, null, token.expiresInSeconds)
                )
            }

            PostAuthStatus.AUTHENTICATED -> {
                val access = tokenIssuer.issueAuthenticationToken(subject = user!!.exKey, tokenType = TokenType.ACCESS)
                val refresh = tokenIssuer.issueRefreshToken(user.exKey)

                PostAuthStatusResponse(
                    status = status,
                    token = BaseTokenResponse(
                        authenticationToken = access.token,
                        refreshToken = refresh.token,
                        expiresInSeconds = access.expiresInSeconds
                    )
                )
            }
        }
    }

//    // TODO 전체 리펙토링
//    fun getAuthStatus(query: GetPostAuthStatusQuery): PostAuthStatusResponse {
//        verifiedIdentityRepository.findVerifiedIdentity(query.verifiedIdentityExKey)
//        ?: throw NotFoundVerifiedIdentityException()
//        val user = userRepository.findByExKey(query.verifiedIdentityExKey)
//        val serviceChannel = when (query.clientPlatform) {
//            ClientPlatform.ANDROID -> ServiceChannel.ANDROID
//            ClientPlatform.IOS -> ServiceChannel.IOS
//            else -> null
//        }
//        var status: PostAuthStatus
//        if (user == null) {
//            status = PostAuthStatus.NOT_REGISTERED
//        } else {
//            if (termQueryService.isAllRequiredAgreed(
//                    query.verifiedIdentityExKey,
//                    query.principalType,
//                    serviceChannel,
//                    query.nationality
//                )
//            ) {
//                status = PostAuthStatus.AUTHENTICATED
//            } else {
//                status = PostAuthStatus.REQUIRE_TERMS
//            }
//        }
//        val subject = when(status){
//            PostAuthStatus.NOT_REGISTERED,PostAuthStatus.REQUIRE_TERMS -> query.verifiedIdentityExKey
//            PostAuthStatus.AUTHENTICATED -> user!!.exKey
//        }
//        val tokenType = when(
//            status
//        ){
//            PostAuthStatus.NOT_REGISTERED,PostAuthStatus.REQUIRE_TERMS -> TokenType.PRE_AUTH
//            PostAuthStatus.AUTHENTICATED -> TokenType.ACCESS
//        }
//        val token = tokenIssuer.issueAuthenticationToken(
//            subject = subject,
//            tokenType =tokenType
//
//        )
//        return PostAuthStatusResponse(status, BaseTokenResponse(token.token, null, token.expiresInSeconds))
//    }
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
