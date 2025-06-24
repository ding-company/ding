package `in`.ding.point.domain

import `in`.ding.point.domain.model.PointWallet
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PointWalletRepository {
    fun save(point: PointWallet): PointWallet
    fun findByCustomerExKey(customerExKey: UUID): PointWallet?
}
