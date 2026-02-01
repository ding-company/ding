package `in`.ding.user.auth.domain.exception

import `in`.ding.common.infra.http.exception.NotFoundException

class OtpNotFound : OtpException, NotFoundException(message = ErrorMessage.OTP_NOT_FOUND)
