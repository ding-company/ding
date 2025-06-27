package `in`.ding.user.user.domain.service

import `in`.ding.common.exception.BadRequestException
import `in`.ding.user.user.domain.model.Email
import `in`.ding.user.user.domain.model.PhoneNumber
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import org.springframework.stereotype.Service

@Service
class ContactValidator {
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
}
