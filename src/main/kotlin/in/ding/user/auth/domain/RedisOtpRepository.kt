package `in`.ding.user.auth.domain

interface RedisOtpRepository {
    fun saveOtp(contact: String, otpCode: String)
    fun findOtp(contact: String): String?

    fun deleteOtp(contact: String)
}
