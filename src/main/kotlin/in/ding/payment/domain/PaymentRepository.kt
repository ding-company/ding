package `in`.ding.payment.domain

import `in`.ding.payment.domain.model.Payment
import java.util.UUID

interface PaymentRepository {
    fun save(domain: Payment): Payment
    fun findByExKey(exKey: UUID): Payment?
}
