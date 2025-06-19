package `in`.ding.customer.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    val exKey: UUID,
    val userExKey: UUID,
    val sellerExKey: UUID,
    val phoneNumber: String? = null,
    val name: String? = null,
    val registeredAt: LocalDateTime,
    val isDeleted: Boolean = false,
    val deletedAt: LocalDateTime? = null
) {
    companion object {
        fun register(userExKey: UUID, sellerExKey: UUID, phoneNumber: String?, name: String?): Customer {
            return Customer(
                exKey = UUID.randomUUID(),
                userExKey = userExKey,
                sellerExKey = sellerExKey,
                phoneNumber = phoneNumber,
                name = name,
                registeredAt = LocalDateTime.now()
            )
        }
    }
}
