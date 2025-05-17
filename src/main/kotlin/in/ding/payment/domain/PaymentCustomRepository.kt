package `in`.ding.payment.domain

import `in`.ding.payment.domain.entity.Payment
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentCustomRepository {
    fun save(payment: Payment)
    fun findByExKey(exKey: UUID): Payment?
}
