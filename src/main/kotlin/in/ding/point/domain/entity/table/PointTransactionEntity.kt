package `in`.ding.point.domain.entity.table

import `in`.ding.common.BaseEntity
import `in`.ding.point.domain.entity.enumerate.PointTransactionStatus
import `in`.ding.point.domain.entity.enumerate.PointTransactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "point_transactions")
class PointTransactionEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(length = 36)
    val pointExKey: UUID,

    @Column(length = 36)
    val customerExKey: UUID,

    @Column(length = 36)
    val sellerExKey: UUID,

    @Column(length = 36)
    val transactionType: PointTransactionType,

    @Column()
    val transactionAmount: BigDecimal,

    @Column()
    val balanceAfterTransaction: BigDecimal,

    // TODO rename column
    @Column(unique = true, length = 36)
    val transactionAt: LocalDateTime,

    @Column()
    val status: PointTransactionStatus,

    @Column()
    val expiredConditionInDays: Int? = null,

    @Column()
    val expiredAt: LocalDateTime? = null,

    @Column()
    val paymentId: Long,

    // TODO rename transactionType = 적립,사용 / transactionType11 = 수동이냐 자동이냐 등
    @Column()
    val transactionType11: Long,

    ) : BaseEntity()
