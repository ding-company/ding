package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.NotFoundException

class OtpNotFound : OtpException, NotFoundException(message = ErrorMessage.OTP_NOT_FOUND)
