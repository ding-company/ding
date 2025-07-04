package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.BadRequestException

class TooManyOtpAttemptsException : OtpException, BadRequestException(message = ErrorMessage.TRIED_OVER_THE_5_TIMES)
