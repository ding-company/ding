package `in`.ding.user.user.domain.exception

import `in`.ding.common.infra.http.exception.NotFoundException

class NotFoundUserException : UserException, NotFoundException(message = ErrorMessage.NOT_FOUND_USER)
