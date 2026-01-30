package `in`.ding.user.term.domain.exception

import `in`.ding.common.exception.ConflictException

class AgreementTargetUnavailable : ConflictException(message = ErrorMessage.AGREEMENT_TARGET_UNAVAILABLE, null)
