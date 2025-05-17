package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.payment.domain.PaymentCustomRepository
import `in`.ding.payment.domain.PaymentMapper
import `in`.ding.payment.domain.entity.Payment
import java.util.*

class PaymentCustomRepositoryImpl(
    private val paymentMapper: PaymentMapper,
    private val paymentRepository: PaymentRepository,
    private val transactionRepository: PaymentTransactionRepository
) : PaymentCustomRepository {
    override fun save(entity: Payment) {
        val newTransactions = entity.getNewTransactions()
        val jpaPayment = paymentMapper.toJpaPayment(entity)
        val jpaTransactions = paymentMapper.toJpaPaymentTransactions(newTransactions)

        paymentRepository.findByExKey(entity.id)?.let { existing ->
            jpaPayment.id = existing.id
        }

        paymentRepository.save(jpaPayment)
        transactionRepository.saveAll(jpaTransactions)
    }

    override fun findByExKey(exKey: UUID): Payment? {
        val paymentEntity = paymentRepository.findByExKey(exKey) ?: return null
        val transactions = transactionRepository.findAllByPaymentExKeyIn(exKey)
        return paymentMapper.toEntity(paymentEntity, transactions)
    }
}
