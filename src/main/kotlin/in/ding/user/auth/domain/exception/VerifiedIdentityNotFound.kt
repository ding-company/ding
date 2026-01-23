package `in`.ding.user.auth.domain.exception

import `in`.ding.common.exception.NotFoundException

class VerifiedIdentityNotFound : OtpException, NotFoundException(message = ErrorMessage.VERIFIED_IDENTITY_NOT_FOUND)
