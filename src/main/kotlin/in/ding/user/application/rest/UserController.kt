package `in`.ding.user.application.rest

import `in`.ding.user.application.dto.command.UserRegisterCommand
import `in`.ding.user.application.dto.http.UserSignupRequest
import `in`.ding.user.application.service.UserAppService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/v1/users")
@RestController
class UserController(private val userAppService: UserAppService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun singUp(
        @Valid @RequestBody body: UserSignupRequest
    ) {
        val command = UserRegisterCommand.of(body)
        userAppService.register(command)
    }
}
