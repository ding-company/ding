package `in`.ding.seller.domain.service

import org.springframework.stereotype.Service
import java.util.UUID

@Service
interface SellerRegisterChecker {
    fun check(userExKey: UUID, storeName: String)
}
