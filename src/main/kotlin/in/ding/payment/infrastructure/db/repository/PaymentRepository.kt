package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.payment.infrastructure.db.table.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentRepository : JpaRepository<PaymentEntity, Long> {
    fun findByExKey(exKey: UUID): PaymentEntity?
}
