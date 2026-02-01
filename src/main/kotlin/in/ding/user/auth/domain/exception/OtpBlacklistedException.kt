package `in`.ding.user.auth.domain.exception

import `in`.ding.common.infra.http.exception.ForbiddenException

class OtpBlacklistedException : ForbiddenException(message = ErrorMessage.BLACKLIST, null)
