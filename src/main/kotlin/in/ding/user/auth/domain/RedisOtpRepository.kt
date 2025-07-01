package `in`.ding.user.auth.domain

import `in`.ding.user.auth.domain.model.vo.OtpCode
import org.springframework.stereotype.Repository

@Repository
interface RedisOtpRepository {
    fun saveOtp(contact: String, otpCode: OtpCode)
    fun findOtp(contact: String): Any?

    fun deleteOtp(contact: String)
}
