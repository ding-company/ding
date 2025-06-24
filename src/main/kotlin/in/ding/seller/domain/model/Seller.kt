package `in`.ding.seller.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Seller(
    val id: SellerId,
    val exKey: UUID,
    val userExKey: UUID,
    val storeName: String,
    val address: String?,
    val registeredAt: LocalDateTime,
) {
    companion object {
        fun register(userExKey: UUID, storeName: String): Seller {
            return Seller(
                id = SellerId.UNASSIGNED,
                exKey = UUID.randomUUID(),
                userExKey = userExKey,
                storeName = storeName,
                address = null,
                registeredAt = LocalDateTime.now(),
            )
        }
    }
}
