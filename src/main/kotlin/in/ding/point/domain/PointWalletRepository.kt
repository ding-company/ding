package `in`.ding.point.domain

import `in`.ding.point.domain.model.PointWallet
import java.util.UUID

interface PointWalletRepository {
    fun save(point: PointWallet): PointWallet
    fun findByCustomerExKey(customerExKey: UUID): PointWallet?
}
