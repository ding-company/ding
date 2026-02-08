package `in`.ding.user.domain.exception

import `in`.ding.common.infra.http.exception.BadRequestException

class NotSupportedNationalityException : RootAuthException, BadRequestException(
    message = ErrorMessage.NOT_SUPPORTED_NATIONALITY
)
