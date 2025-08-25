package `in`.ding.seller.domain.service

import `in`.ding.seller.domain.exception.DuplicatedSellerNameException
import `in`.ding.seller.infrastructure.db.repository.SellerJpaRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SellerRegisterCheckerImpl(private val repository: SellerJpaRepository) : SellerRegisterChecker {
    override fun check(userExKey: UUID, storeName: String) {
        val existedSeller = repository.findByUserExKeyAndStoreName(userExKey, storeName)
        if (existedSeller != null) {
            throw DuplicatedSellerNameException()
        }
    }
}
