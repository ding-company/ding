package `in`.ding.payment.domain

import `in`.ding.payment.domain.model.Payment
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentRepository {
    fun save(domain: Payment)
    fun findByExKey(exKey: UUID): Payment?
}
