package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.payment.domain.PaymentMapper
import `in`.ding.payment.domain.PaymentRepository
import `in`.ding.payment.domain.model.Payment
import java.util.*

class PaymentRepositoryImpl(
    private val paymentMapper: PaymentMapper,
    private val paymentRepository: PaymentJpaRepository,
    private val transactionRepository: PaymentJpaTransactionRepository
) : PaymentRepository {

    override fun save(domain: Payment) {
        val jpaPayment = paymentMapper.toEntity(domain).also {
            if (domain.id.isAssigned()) {
                it.id = domain.id.value
            }
        }

        val newTransactions = domain.getTransactions()
            .filter { it.isNew() }
            .let(paymentMapper::toJpaPaymentTransactions)

        paymentRepository.save(jpaPayment)
        transactionRepository.saveAll(newTransactions)
    }

    override fun findByExKey(exKey: UUID): Payment? {
        val paymentEntity = paymentRepository.findByExKey(exKey) ?: return null
        val transactions = transactionRepository.findAllByPaymentExKeyIn(exKey)
        return paymentMapper.toDomain(paymentEntity, transactions)
    }
}
