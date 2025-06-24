package `in`.ding.seller.infrastructure.db.repository

import `in`.ding.common.DomainID
import `in`.ding.seller.infrastructure.db.table.SellerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SellerJpaRepository : JpaRepository<SellerEntity, DomainID> {
    fun findByUserExKeyAndStoreName(userExKey: UUID, storeName: String): SellerEntity?
}
