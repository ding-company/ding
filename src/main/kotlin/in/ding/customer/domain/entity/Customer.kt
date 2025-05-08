package `in`.ding.customer.domain.entity

import java.time.LocalDateTime
import java.util.UUID

class Customer private constructor(
    val externalKey: UUID,
    val userExKey: UUID,
    val phoneNumber: String? = null,
    val name: String? = null,
    val registeredAt: LocalDateTime,
    val isDeleted: Boolean = false,
    val deletedAt: LocalDateTime? = null
) {
    companion object {
        fun register(userExKey: UUID, phoneNumber: String?, name: String?): Customer {
            return Customer(
                externalKey = UUID.randomUUID(),
                userExKey = userExKey,
                phoneNumber = phoneNumber,
                name = name,
                registeredAt = LocalDateTime.now()
            )
        }
    }
    fun getPoint() {}
    fun getPointTxList() {}
}
