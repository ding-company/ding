package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.common.infra.jpa.ID
import `in`.ding.payment.infrastructure.db.table.PaymentTransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PaymentTransactionJpaRepository : JpaRepository<PaymentTransactionEntity, ID> {
    fun findAllByPaymentExKey(paymentExKey: UUID): List<PaymentTransactionEntity>
}
