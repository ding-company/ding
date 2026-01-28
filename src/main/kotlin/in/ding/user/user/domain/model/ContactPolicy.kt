package `in`.ding.user.user.domain.model

import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.user.domain.model.enumerate.ContactType
import org.springframework.stereotype.Component

@Component
object ContactPolicy {
    fun determineContactType(nationality: UserNationality): ContactType = when (nationality) {
        UserNationality.KR -> ContactType.PHONE_NUMBER
        else -> ContactType.EMAIL
    }
}
