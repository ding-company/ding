package `in`.ding.user.auth.application.service.support.ttl

enum class ExpiryPolicy {
    OTP_SESSION,
    RETRY_TRACK,
    BLOCK,
    SHORT_LIVED_IDENTITY
}
