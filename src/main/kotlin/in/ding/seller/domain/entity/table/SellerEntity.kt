package `in`.ding.seller.domain.entity.table

import `in`.ding.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Table(name = "sellers")
class SellerEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,
    @Column(length = 36)
    val userExKey: UUID,

    @Column(length = 30)
    val storeName: String,

    @Column(length = 30)
    val address: String?,

    @Column()
    val registeredAt: LocalDateTime,
) : BaseEntity() {
    companion object {
        fun register(userExKey: UUID, storeName: String): SellerEntity {
            return SellerEntity(
                exKey = UUID.randomUUID(),
                userExKey = userExKey,
                storeName = storeName,
                address = null,
                registeredAt = LocalDateTime.now(),
            )
        }
    }
}
