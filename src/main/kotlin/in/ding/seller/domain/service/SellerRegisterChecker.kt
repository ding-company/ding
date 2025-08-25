package `in`.ding.seller.domain.service

import java.util.UUID

interface SellerRegisterChecker {
    fun check(userExKey: UUID, storeName: String)
}
