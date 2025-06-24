package `in`.ding.seller.domain

import `in`.ding.seller.domain.model.Seller

interface SellerRepository {
    fun save(domain: Seller): Seller
}
