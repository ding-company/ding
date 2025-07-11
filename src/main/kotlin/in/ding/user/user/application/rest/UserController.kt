package `in`.ding.user.user.application.rest

import `in`.ding.common.auth.AuthUser
import `in`.ding.user.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.user.application.dto.http.UserOtpRequest
import `in`.ding.user.user.application.service.UserAppService
import jakarta.validation.Valid
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody body: UserOtpRequest
    ) {
        val command = UserRegisterCommand.of(body)
        userAppService.register(command)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-in")
    fun signIn(
        @Valid @RequestBody body: UserOtpRequest
    ) {
        val command = UserRegisterCommand.of(body)
        userAppService.register(command)
    }
}
