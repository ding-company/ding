package `in`.ding.common.infra.security.jwt.exception

import `in`.ding.common.infra.http.exception.UnauthorizedException

class InvalidTokenException : TokenException, UnauthorizedException(message = ErrorMessage.INVALID_TOKEN)
