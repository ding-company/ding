package `in`.ding.seller.application.service

import `in`.ding.seller.application.dto.SellerRegisterCommand
import `in`.ding.seller.domain.SellerRepository
import `in`.ding.seller.domain.model.Seller
import `in`.ding.seller.domain.service.SellerRegisterChecker
import org.springframework.stereotype.Service

@Service
class SellerRegisterServiceImpl(
    private val sellerRegisterChecker: SellerRegisterChecker,
    private val repository: SellerRepository
) : SellerRegisterService {
    override fun register(command: SellerRegisterCommand) {
        sellerRegisterChecker.check(command.userExKey, command.storeName)
        val seller = Seller.register(command.userExKey, command.storeName)
        repository.save(seller)
    }
}
