package `in`.ding.user.user.domain.model

import `in`.ding.user.user.domain.exception.ErrorMessage

@JvmInline
value class PhoneNumber(val value: String) {
    init {
        require(value.matches(Regex("^01(?:0|1|[6-9])-(?:\\d{3,4})-\\d{4}\$"))) {
            ErrorMessage.INVALID_PHONE_NUMBER
        }
    }
}
