package `in`.ding.user.auth.application.service

import `in`.ding.common.exception.BaseHttpException
import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.domain.RedisOtpRepository
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.exception.NotFoundUserException
import `in`.ding.user.user.domain.model.ContactPolicy.determineContactType
import `in`.ding.user.user.domain.model.Email
import `in`.ding.user.user.domain.model.PhoneNumber
import `in`.ding.user.user.domain.model.enumerate.ContactType

class OtpServiceImpl(
    private val publisher: AuthEventPublisher,
    private val otpRedisOtpRepository: RedisOtpRepository,
    private val userRepository: UserRepository,
    private val userRedisRepository: UserRedisRepository,
) : OtpService {
    override fun issue(command: OtpIssueCommand) {
        val contactType = determineContactType(command.nationality)
        val contact = when (contactType) {
            ContactType.PHONE_NUMBER -> PhoneNumber(command.contact)
            else -> Email(command.contact)
        }
        publisher.publish(
            OtpRequestedEvent(
                contact = contact.toString(),
                nationality = command.nationality,
                requestId = command.requestId
            )
        )
    }

    override fun verify(command: OtpVerifyCommand) {
        val otp = otpRedisOtpRepository.findOtp(contact = command.contact)
            ?: throw OtpNotFound()
        val user = userRedisRepository.findTemporaryUser(command.contact)
            ?: throw NotFoundUserException()

        try {
            otp.verify(command.otpCode)
            userRepository.save(user)
        } catch (e: BaseHttpException) {
            when (e) {
                is OtpNotFound,
                is InvalidOtpException,
                is ExpiredOtpException -> {
                    otp.incrementTry()
                    otpRedisOtpRepository.saveOtp(command.contact, otp)
                    throw e
                }
                else -> throw e
            }
        }

        TODO("재검증 및 최대 검증횟수 초과 관련 로직 필요")
    }
}
