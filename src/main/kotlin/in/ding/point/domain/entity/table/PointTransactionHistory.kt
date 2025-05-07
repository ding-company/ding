package `in`.ding.point.domain.entity.table

import `in`.ding.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "point_transaction_histories")
class PointTransactionHistory(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36)
    val pointExKey: UUID,

    @Column(length = 36)
    val pointTransactionExKey: UUID,
) : BaseEntity()
