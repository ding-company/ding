package `in`.ding.point.infrastructure.db.repository

import `in`.ding.point.domain.PointWalletRepository
import `in`.ding.point.domain.model.PointWallet
import org.springframework.stereotype.Repository
import java.util.UUID

// todo 구현하기
@Repository
class PointWalletRepositoryImpl : PointWalletRepository {
    override fun save(point: PointWallet): PointWallet {
        throw NotImplementedError()
    }
    override fun findByCustomerExKey(customerExKey: UUID): PointWallet? {
        throw NotImplementedError()
    }
}
