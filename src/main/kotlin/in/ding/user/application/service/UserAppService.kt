package `in`.ding.user.application.service

import `in`.ding.user.application.dto.enumerate.UserSignupRequest
import `in`.ding.user.domain.service.UserCommandService
import `in`.ding.user.domain.service.dto.UserRegisterDTO
import org.springframework.stereotype.Service

@Service
class UserAppService(private val userCommandService: UserCommandService) {
    fun register(request: UserSignupRequest) {
        userCommandService.register(dto = UserRegisterDTO.of(request = request))
    }
}
