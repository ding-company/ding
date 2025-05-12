package `in`.ding.payment.infrastructure.db.repository

import `in`.ding.payment.domain.entity.table.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<Payment, Long>
