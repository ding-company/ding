package `in`.ding.user.auth.application.service.auth

import `in`.ding.common.infra.http.ClientPlatform
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import java.util.*

data class GetPostAuthStatusQuery(
    val userExKey: UUID,
    val principalType: PrincipalType,
    val clientPlatform: ClientPlatform?,
    val nationality: UserNationality
)
