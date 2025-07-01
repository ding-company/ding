package `in`.ding.user.user.domain.model

import `in`.ding.common.exception.BadRequestException
import `in`.ding.user.user.domain.model.enumerate.ContactType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import org.springframework.stereotype.Component

@Component
object ContactPolicy {
    fun validate(nationality: UserNationality, phoneNumber: String?, email: String?): String {
        val phoneNumberForValidation = phoneNumber?.let { PhoneNumber(phoneNumber) }
        val emailForValidation = email?.let { Email(email) }
        when (nationality) {
            UserNationality.KR -> {
                phoneNumberForValidation ?: throw BadRequestException()
                return phoneNumber
            }
            else -> {
                emailForValidation ?: throw BadRequestException()
                return email
            }
        }
    }
    fun determineContactType(nationality: UserNationality): ContactType = when (nationality) {
        UserNationality.KR -> ContactType.PHONE_NUMBER
        else -> ContactType.EMAIL
    }
}
