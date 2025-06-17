package `in`.ding.customer.infrastructure.db.table

import `in`.ding.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "customers")
class CustomerEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36)
    val userExKey: UUID,

    @Column(length = 36)
    val sellerExKey: UUID,

    @Column(unique = true, length = 20)
    val phoneNumber: String? = null,

    @Column(length = 40)
    val name: String? = null,
    @Column()
    val registeredAt: LocalDateTime,
) : BaseEntity()
