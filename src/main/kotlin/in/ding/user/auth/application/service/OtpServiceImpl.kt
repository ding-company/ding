package `in`.ding.user.auth.application.service

import `in`.ding.common.exception.BaseHttpException
import `in`.ding.user.auth.application.dto.command.OtpIssueCommand
import `in`.ding.user.auth.application.dto.command.OtpVerifyCommand
import `in`.ding.user.auth.domain.RedisOtpRepository
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.auth.domain.exception.ExpiredOtpException
import `in`.ding.user.auth.domain.exception.InvalidOtpException
import `in`.ding.user.auth.domain.exception.OtpNotFound
import `in`.ding.user.auth.domain.service.OtpBlockChecker
import `in`.ding.user.auth.infrastructure.messaging.kafka.AuthEventPublisher
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.UserRepository
import `in`.ding.user.user.domain.exception.NotFoundUserException

class OtpServiceImpl(
    private val blockChecker: OtpBlockChecker,
    private val otpRepository: RedisOtpRepository,
    private val userRepository: UserRepository,
    private val userRedisRepository: UserRedisRepository,
    private val publisher: AuthEventPublisher,
) : OtpService {
    override fun issue(command: OtpIssueCommand) {
        blockChecker.check(command.contact)

        publisher.publish(OtpRequestedEvent(command.contact, command.nationality, command.requestId))
    }

    override fun verify(command: OtpVerifyCommand) {
        blockChecker.check(command.contact)

        val otp = otpRepository.findOtp(command.contact) ?: throw OtpNotFound()
        val user = userRedisRepository.findTemporaryUser(command.contact) ?: throw NotFoundUserException()

        try {
            val verifiedOtp = otp.verify(command.otpCode)
            otpRepository.saveOtp(command.contact, verifiedOtp)
            userRepository.save(user)
        } catch (e: BaseHttpException) {
            when (e) {
                is InvalidOtpException,
                is ExpiredOtpException -> {
                    val updatedOtp = otp.incrementTry()
                    otpRepository.saveOtp(command.contact, updatedOtp)
                    blockChecker.blockIfExceedsLimit(command.contact, updatedOtp.tryCount)
                }
            }
            throw e
        }
    }
}
