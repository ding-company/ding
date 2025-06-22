package `in`.ding.customer.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.customer.domain.model.enumerate.CustomerStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    val status: CustomerStatus,

    @Column()
    val registeredAt: LocalDateTime,
) : SoftDeletedBaseEntity()
