package `in`.ding.user.user.application.rest

import `in`.ding.common.auth.AuthUser
import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.http.RegisterUserRequest
import `in`.ding.user.user.application.service.UserAppService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/v1/users")
@RestController
class UserController(private val userAppService: UserAppService) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    fun getMe(
        @AuthenticationPrincipal user: AuthUser,
    ) {
        userAppService.getByExKey(user.exKey)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    fun registerUser(
        @RequestBody request: RegisterUserRequest,
        @AuthenticationPrincipal user: AuthUser,
    ) {
        userAppService.register(UserRegisterCommand.of(request, user.exKey))
    }
}
