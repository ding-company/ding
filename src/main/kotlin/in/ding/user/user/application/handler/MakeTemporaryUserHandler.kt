package `in`.ding.user.user.application.handler

import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.user.domain.UserRedisRepository
import `in`.ding.user.user.domain.model.ContactPolicy
import `in`.ding.user.user.domain.model.User
import org.springframework.stereotype.Component

@Component
class MakeTemporaryUserHandler(
    private val userRedisRepository: UserRedisRepository,
    private val contactPolicy: ContactPolicy,
) {
    fun handle(event: OtpRequestedEvent) {
        val contactType = contactPolicy.determineContactType(nationality = event.nationality)
        val user = User.makeTempUser(
            contact = event.contact,
            contactType = contactType,
            nationality = event.nationality
        )
        userRedisRepository.saveTemporaryUser(contact = event.contact, userData = user)
    }
}
