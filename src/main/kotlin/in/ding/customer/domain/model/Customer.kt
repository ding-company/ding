package `in`.ding.customer.domain.model

import `in`.ding.common.DomainID
import java.time.LocalDateTime
import java.util.*

data class Customer(
    val id: DomainID,
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
                id = DomainID.UNASSIGNED,
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
