package `in`.ding.user.auth.domain.model.vo

@JvmInline
value class OtpCode(val value: String) {
    init { require(value.matches(Regex("\\d{6}"))) }
}
