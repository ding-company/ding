package `in`.ding.seller.infrastructure.mapper

import `in`.ding.common.ErrorMessage
import `in`.ding.seller.domain.model.Seller
import `in`.ding.seller.domain.model.SellerId
import `in`.ding.seller.infrastructure.db.table.SellerEntity
import org.springframework.stereotype.Component

@Component
class SellerMapper {
    fun toDomain(entity: SellerEntity): Seller {
        return Seller(
            id = SellerId(requireNotNull(entity.id) { ErrorMessage.ID_IS_NULL }),
            exKey = entity.exKey,
            userExKey = entity.userExKey,
            storeName = entity.storeName,
            address = entity.address,
            registeredAt = entity.registeredAt,
        )
    }
    fun toEntity(domain: Seller): SellerEntity {
        return SellerEntity(
            exKey = domain.exKey,
            userExKey = domain.userExKey,
            storeName = domain.storeName,
            address = domain.address,
            registeredAt = domain.registeredAt,
        )
    }
}
