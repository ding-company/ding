package `in`.ding.user.term.application.service.agree

import `in`.ding.user.auth.domain.exception.NotFoundVerifiedIdentityException
import `in`.ding.user.auth.domain.repository.RedisVerifiedIdentityRepository
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TermAgreeAppService(
    private val verifiedIdentityRepository: RedisVerifiedIdentityRepository,
    private val userRepository: UserRepository,
) {

    fun agrees(command: TermAgreesCommand) {
        val verifiedIdentity = verifiedIdentityRepository.findVerifiedIdentity(command.verifiedIdentityExKey)
            ?: throw NotFoundVerifiedIdentityException()
        // TODO 약관이 존재하는지 조회
        val user = User.register(
            exKey = UUID.randomUUID(),
            phoneNumber = verifiedIdentity.phoneNumber,
            email = verifiedIdentity.email,
            nationality = verifiedIdentity.nationality
        )
        // TODO 약관 동의
        userRepository.save(user)
    }
}
