package `in`.ding.seller.application.service

import `in`.ding.seller.application.dto.SellerRegisterCommand
import `in`.ding.seller.domain.entity.table.SellerEntity
import `in`.ding.seller.domain.service.SellerRegisterChecker
import `in`.ding.seller.infrastructure.db.repository.SellerRepository

class SellerRegisterServiceImpl(
    private val sellerRegisterChecker: SellerRegisterChecker,
    private val repository: SellerRepository
) : SellerRegisterService {
    override fun register(command: SellerRegisterCommand) {
        sellerRegisterChecker.check(command.userExKey, command.storeName)
        val seller = SellerEntity.register(command.userExKey, command.storeName)
        repository.save(seller)
    }
}
