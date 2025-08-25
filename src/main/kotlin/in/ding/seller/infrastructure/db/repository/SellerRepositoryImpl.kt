package `in`.ding.seller.infrastructure.db.repository

import `in`.ding.seller.domain.SellerRepository
import `in`.ding.seller.domain.model.Seller
import `in`.ding.seller.infrastructure.mapper.SellerMapper
import org.springframework.stereotype.Repository

@Repository
class SellerRepositoryImpl(
    private val mapper: SellerMapper,
    private val jpaRepository: SellerJpaRepository
) : SellerRepository {
    override fun save(domain: Seller): Seller {
        val entity = mapper.toEntity(domain).also {
            if (domain.id.isAssigned()) {
                it.id = domain.id.value
            }
        }
        val savedSeller = jpaRepository.save(entity)
        return mapper.toDomain(savedSeller)
    }
}
