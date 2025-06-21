package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.payment.domain.PaymentMapper
import `in`.ding.payment.domain.PaymentRepository
import `in`.ding.payment.domain.model.Payment
import java.util.UUID

class PaymentRepositoryImpl(
    private val mapper: PaymentMapper,
    private val paymentRepository: PaymentJpaRepository,
    private val transactionRepository: PaymentTransactionJpaRepository,
) : PaymentRepository {

    override fun save(domain: Payment): Payment {
        val jpaPayment = mapper.toEntity(domain).also {
            if (domain.id.isAssigned()) {
                it.id = domain.id.value
            }
        }
        val newTransactions = domain.getTransactions()
            .filter { it.isNew() }
            .let(mapper::toJpaPaymentTransactions)

        val savedPayment = paymentRepository.save(jpaPayment)
        val savedTx = transactionRepository.saveAll(newTransactions)
        return mapper.toDomain(savedPayment, savedTx)
    }

    override fun findByExKey(exKey: UUID): Payment? {
        val paymentEntity = paymentRepository.findByExKey(exKey) ?: return null
        val transactions = transactionRepository.findAllByPaymentExKey(exKey)
        return mapper.toDomain(paymentEntity, transactions)
    }
}
