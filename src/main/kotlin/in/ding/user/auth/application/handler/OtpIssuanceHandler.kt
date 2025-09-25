package `in`.ding.user.auth.application.handler

import `in`.ding.user.auth.domain.event.OtpIssuedEvent
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.model.OtpSession
import `in`.ding.user.auth.domain.model.vo.OtpCode
import `in`.ding.user.auth.domain.repository.RedisOtpRepository
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.model.ContactPolicy
import org.springframework.stereotype.Component

@Component
class OtpIssuanceHandler(
    private val otpRedisRepository: RedisOtpRepository,
    private val contactPolicy: ContactPolicy,
    private val publisher: AuthEventPublisher
) {
    fun handle(event: OtpRequestedEvent) {
        val otpCode = OtpCode.generate()
        val otp = OtpSession.create(contact = event.contact, code = otpCode)
        val contactType = contactPolicy.determineContactType(nationality = event.nationality)
        otpRedisRepository.saveOtp(contact = event.contact, otp = otp, OtpSession.getOtpTtl())
        publisher.publish(OtpIssuedEvent.of(otp, contactType))
    }
}
