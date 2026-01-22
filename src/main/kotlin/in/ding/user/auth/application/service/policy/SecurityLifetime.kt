package `in`.ding.user.auth.application.service.policy

enum class SecurityLifetime {
    OTP_RETRY_TRACK,
    OTP_BLOCK,
    OTP_BLACKLIST
}
