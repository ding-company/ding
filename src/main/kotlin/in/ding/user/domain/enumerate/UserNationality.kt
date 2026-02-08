package `in`.ding.user.domain.enumerate

import `in`.ding.user.domain.exception.NotSupportedNationalityException

enum class UserNationality {
    KR,
    UK ;

    companion object {
        fun from(value: String): UserNationality =
            runCatching { valueOf(value.uppercase()) }
                .getOrElse { throw NotSupportedNationalityException() }
    }
}
