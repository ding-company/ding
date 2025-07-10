package `in`.ding.user.auth.domain.repository

import `in`.ding.user.auth.domain.model.OtpSession
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
interface RedisOtpRepository {
    fun saveOtp(contact: String, otp: OtpSession, ttl: Duration)
    fun findOtp(contact: String): OtpSession?

    fun deleteOtp(contact: String)
}
