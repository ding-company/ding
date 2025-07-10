package `in`.ding.user.auth.application.handler

import `in`.ding.user.auth.domain.event.OtpIssuedEvent
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.model.ContactPolicy
import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class OtpIssuanceHandler(
    private val otpRedisRepository: RedisOtpRepository,
    private val userRedisRepository: UserRedisRepository,
    private val contactPolicy: ContactPolicy,
    private val publisher: AuthEventPublisher
) {
    fun handle(event: OtpRequestedEvent) {
        val otpCode = OtpCode.generate()
        val otp = OtpSession.create(contact = event.contact, code = otpCode)
        val contactType = contactPolicy.determineContactType(nationality = event.nationality)
        val user = User.makeTempUser(
            contact = event.contact,
            contactType = contactType,
            nationality = event.nationality
        )
        otpRedisRepository.saveOtp(contact = event.contact, otp = otp, OtpSession.getOtpTtl())
        userRedisRepository.saveTemporaryUser(contact = event.contact, userData = user)
        publisher.publish(OtpIssuedEvent.of(otp, contactType))
    }
}
