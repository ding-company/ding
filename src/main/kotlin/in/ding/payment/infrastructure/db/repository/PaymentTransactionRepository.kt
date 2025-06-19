package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.common.DomainID
import `in`.ding.payment.infrastructure.db.table.PaymentTransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PaymentTransactionRepository : JpaRepository<PaymentTransactionEntity, DomainID> {
    fun findAllByPaymentExKeyIn(paymentExKey: UUID): List<PaymentTransactionEntity>
}
