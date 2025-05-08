package `in`.ding.seller.infrastructure.db.repository

import `in`.ding.seller.domain.entity.table.SellerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SellerRepository : JpaRepository<SellerEntity, Long> {
    fun findByUserExKeyAndStoreName(userExKey: UUID, storeName: String): SellerEntity?
}
