package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.UnauthorizedException

class InvalidOtpException : OtpException, UnauthorizedException(message = ErrorMessage.WRONG_OTP_CODE)
