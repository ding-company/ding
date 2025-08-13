package `in`.ding.user.user.application.dto.command

import `in`.ding.user.user.application.dto.http.RegisterUserRequest
import `in`.ding.user.user.application.dto.http.TermsAgreementRequest
import java.util.UUID

class UserRegisterCommand(
    val userExKey: UUID,
    val termsAgreement: List<TermsAgreementRequest>

) {
    companion object {
        fun of(request: RegisterUserRequest, userExKey: UUID): UserRegisterCommand {
            return UserRegisterCommand(
                userExKey = userExKey,
                termsAgreement = request.termsAgreement
            )
        }
    }
}
