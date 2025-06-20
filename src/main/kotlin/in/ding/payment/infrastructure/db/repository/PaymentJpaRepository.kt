package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.common.ID
import `in`.ding.payment.infrastructure.db.table.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PaymentJpaRepository : JpaRepository<PaymentEntity, ID> {
    fun findByExKey(exKey: UUID): PaymentEntity?
}
