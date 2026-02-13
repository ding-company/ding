package `in`.ding.user.auth.domain.exception

import `in`.ding.common.infra.http.exception.NotFoundException

class NotFoundVerifiedIdentityException : OtpException, NotFoundException(
    message = ErrorMessage.VERIFIED_IDENTITY_NOT_FOUND
)
