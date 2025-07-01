package `in`.ding.user.auth.application.handler

import `in`.ding.user.auth.domain.RedisOtpRepository
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class OtpIssuanceHandler(
    private val otpRedisRepository: RedisOtpRepository,
    private val userRedisRepository: UserRedisRepository
) {
    fun handle(event: OtpRequestedEvent) {
        val otpCode = OtpCode.generate()
        OtpSession.create(contact = event.contact, code = otpCode)
        val user = User.register(phoneNumber = event.contact, email = event.contact, nationality = event.nationality)
        otpRedisRepository.saveOtp(contact = event.contact, otpCode = otpCode)
        userRedisRepository.saveTemporaryUser(contact = event.contact, userData = user)
        TODO("메세지 전송")
    }
}
