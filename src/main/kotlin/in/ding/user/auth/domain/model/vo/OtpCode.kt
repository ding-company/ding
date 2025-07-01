package `in`.ding.user.auth.domain.model.vo

@Suppress("MagicNumber")
@JvmInline
value class OtpCode(val value: String) {
    init { require(value.matches(Regex("\\d{6}"))) }
    companion object {
        fun generate(): OtpCode {
            val code = (100000..999999).random().toString()
            return OtpCode(code)
        }
    }
}
