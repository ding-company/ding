package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.UnauthorizedException

class ExpiredOtpException : OtpException, UnauthorizedException(message = ErrorMessage.EXPIRED_OTB)
