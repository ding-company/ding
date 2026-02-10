package `in`.ding.common.infra.security.jwt.exception

import `in`.ding.common.infra.http.exception.UnauthorizedException

class TokenExpiredException : TokenException, UnauthorizedException(message = ErrorMessage.TOKEN_EXPIRED)
