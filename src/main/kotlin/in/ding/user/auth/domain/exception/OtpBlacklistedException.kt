package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.ForbiddenException

class OtpBlacklistedException : ForbiddenException(message = ErrorMessage.BLACKLIST, null)
