package `in`.ding.point.domain.entity.table

import `in`.ding.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "points")
class PointEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36)
    val customerExKey: UUID,

    // TODO rename column
    @Column()
    val balance: BigDecimal = BigDecimal.ZERO,

    // TODO rename column
    @Column()
    val totalAccumulatedAmount: BigDecimal = BigDecimal.ZERO,

    ) : BaseEntity()
