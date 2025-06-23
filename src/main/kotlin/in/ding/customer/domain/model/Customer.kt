package `in`.ding.customer.domain.model

import `in`.ding.customer.domain.model.enumerate.CustomerStatus
import java.time.LocalDateTime
import java.util.*

data class Customer(
    val id: CustomerID,
    val exKey: UUID,
    val userExKey: UUID,
    val sellerExKey: UUID,
    val phoneNumber: String? = null,
    val name: String? = null,
    val registeredAt: LocalDateTime,
    val isDeleted: Boolean = false,
    val status: CustomerStatus = CustomerStatus.TEMPORARY,
    val deletedAt: LocalDateTime? = null
) {
    companion object {
        fun createTemporary(userExKey: UUID, sellerExKey: UUID, phoneNumber: String?, name: String?): Customer {
            return Customer(
                id = CustomerID.UNASSIGNED,
                exKey = UUID.randomUUID(),
                userExKey = userExKey,
                sellerExKey = sellerExKey,
                phoneNumber = phoneNumber,
                name = name,
                status = CustomerStatus.TEMPORARY,
                registeredAt = LocalDateTime.now()
            )
        }
    }

    fun register(): Customer {
        require(status == CustomerStatus.TEMPORARY) { "이미 정식 등록된 고객입니다." }

        return this.copy(
            status = CustomerStatus.REGISTERED
        )
    } }
