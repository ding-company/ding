package `in`.ding.user.auth.domain.model

import `in`.ding.user.auth.domain.event.AuthEvent
import `in`.ding.user.auth.domain.event.OtpRequestedEvent
import `in`.ding.user.user.domain.model.enumerate.UserNationality

data class Otp(
    val contact: String,
    val nationality: UserNationality,
    private val event: MutableList<AuthEvent> = mutableListOf()
) {
    val events: List<AuthEvent> = event.toList()
    fun requestOtp(requestId: String) {
        event.add(
            OtpRequestedEvent(
                contact = contact,
                nationality = nationality,
                requestId = requestId
            )
        )
        TODO("최대횟수 요청 확인")
    }
    fun clearEvents() {
        event.clear()
    }
}
