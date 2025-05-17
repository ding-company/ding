package `in`.ding.payment.domain

import `in`.ding.payment.domain.entity.Payment
import `in`.ding.payment.domain.entity.PaymentTransaction
import `in`.ding.payment.infrastructure.db.table.PaymentEntity
import `in`.ding.payment.infrastructure.db.table.PaymentTransactionEntity

class PaymentMapper {
    fun toEntity(payment: PaymentEntity, paymentTransactions: List<PaymentTransactionEntity>): Payment {
        return Payment(
            id = payment.exKey,
            sellerExKey = payment.sellerExKey,
            amount = payment.amount,
            status = payment.status,
            authorizedAt = payment.authorizedAt,
            capturedAt = payment.capturedAt,
            refundedAt = payment.refundedAt,
            transactions = paymentTransactions.map {
                PaymentTransaction(
                    exKey = it.exKey,
                    amount = it.amount,
                    type = it.type,
                    transactionAt = it.transactionAt,
                )
            }.toMutableList()
        )
    }
    fun toJpaPayment(entity: Payment): PaymentEntity {
        return PaymentEntity(
            exKey = entity.id,
            amount = entity.amount,
            sellerExKey = entity.sellerExKey,
            status = entity.status,
            authorizedAt = entity.authorizedAt,
            capturedAt = entity.capturedAt,
            refundedAt = entity.refundedAt,
        )
    }
    fun toJpaPaymentTransactions(entities: List<PaymentTransaction>): List<PaymentTransactionEntity> {
        return entities.map {
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
