package `in`.ding.user.auth.domain

import `in`.ding.user.auth.domain.model.OtpSession
import org.springframework.stereotype.Repository

@Repository
interface RedisOtpRepository {
    fun saveOtp(contact: String, otp: OtpSession)
    fun findOtp(contact: String): OtpSession?

    fun deleteOtp(contact: String)
}
