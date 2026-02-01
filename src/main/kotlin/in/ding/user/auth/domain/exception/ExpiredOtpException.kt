package `in`.ding.user.auth.domain.exception

import `in`.ding.common.infra.http.exception.UnauthorizedException

class ExpiredOtpException : OtpException, UnauthorizedException(message = ErrorMessage.EXPIRED_OTB)
