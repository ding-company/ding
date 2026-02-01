package `in`.ding.payment.infrastructure.mapper

import `in`.ding.common.domain.event.DomainID
import `in`.ding.common.infra.http.ErrorMessage
import `in`.ding.payment.domain.model.Payment
import `in`.ding.payment.domain.model.PaymentID
import `in`.ding.payment.domain.model.PaymentTransaction
import `in`.ding.payment.infrastructure.db.table.PaymentEntity
import `in`.ding.payment.infrastructure.db.table.PaymentTransactionEntity
import org.springframework.stereotype.Component

@Component
class PaymentMapper {
    fun toDomain(payment: PaymentEntity, paymentTransactions: List<PaymentTransactionEntity>): Payment {
        return Payment(
            id = PaymentID(requireNotNull(payment.id) { ErrorMessage.ID_IS_NULL }),
            exKey = payment.exKey,
            sellerExKey = payment.sellerExKey,
            amount = payment.amount,
            status = payment.status,
            authorizedAt = payment.authorizedAt,
            capturedAt = payment.capturedAt,
            refundedAt = payment.refundedAt,
            transactions = paymentTransactions.map {
                PaymentTransaction(
                    id = DomainID(requireNotNull(it.id) { ErrorMessage.ID_IS_NULL }),
                    exKey = it.exKey,
                    amount = it.amount,
                    type = it.type,
                    transactionAt = it.transactionAt,
                )
            }.toMutableList()
        )
    }
    fun toEntity(domain: Payment): PaymentEntity {
        return PaymentEntity(
            exKey = domain.exKey,
            amount = domain.amount,
            sellerExKey = domain.sellerExKey,
            status = domain.status,
            authorizedAt = domain.authorizedAt,
            capturedAt = domain.capturedAt,
            refundedAt = domain.refundedAt,
        )
    }
    fun toJpaPaymentTransactions(domains: List<PaymentTransaction>): List<PaymentTransactionEntity> {
        return domains.map {
            PaymentTransactionEntity(
                exKey = it.exKey,
                paymentExKey = it.exKey,
                amount = it.amount,
                type = it.type,
                transactionAt = it.transactionAt,
            )
        }
    }
}
