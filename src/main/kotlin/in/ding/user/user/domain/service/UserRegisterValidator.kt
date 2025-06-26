package `in`.ding.user.user.domain.service

import `in`.ding.common.exception.BadRequestException
import `in`.ding.user.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class UserRegisterValidator(
    private val userRepository: UserRepository
) {
    fun validateUniquePhoneNumber(phoneNumber: String?) {
        phoneNumber ?: return
        if (userRepository.findByPhoneNumber(phoneNumber) != null) {
            throw BadRequestException("Phone number '$phoneNumber' is already in use.")
        }
    }

    fun validateUniqueEmail(email: String?) {
        email ?: return
        if (userRepository.findByEmail(email) != null) {
            throw BadRequestException("Email '$email' is already in use.")
        }
    }
}
