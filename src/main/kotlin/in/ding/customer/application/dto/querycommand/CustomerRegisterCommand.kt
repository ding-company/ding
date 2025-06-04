package `in`.ding.customer.application.dto.querycommand

import `in`.ding.customer.application.dto.http.PostCustomerRequest
import `in`.ding.user.domain.model.enumerate.UserNationality
import java.util.*

data class CustomerRegisterCommand(
    val phoneNumber: String?,
    val email: String?,
    val name: String?,
    val nationality: UserNationality,
    val sellerExKey: UUID,
) {
    companion object {
        fun of(requestBody: PostCustomerRequest, sellerExKey: UUID): CustomerRegisterCommand {
            return CustomerRegisterCommand(
                phoneNumber = requestBody.phoneNumber,
                email = requestBody.email,
                name = requestBody.name,
                nationality = requestBody.nationality,
                sellerExKey = sellerExKey
            )
        }
    }
}
