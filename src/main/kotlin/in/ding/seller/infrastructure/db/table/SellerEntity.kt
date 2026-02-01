package `in`.ding.seller.infrastructure.db.table

import `in`.ding.common.infra.jpa.SoftDeletedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
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
) : SoftDeletedBaseEntity()
