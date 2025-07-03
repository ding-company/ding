package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.NotFoundException

class OtpNotFound : OtpException, NotFoundException(message = "Not Found Otp")
