package `in`.ding.seller.application.service

import `in`.ding.seller.application.dto.SellerRegisterCommand

interface SellerRegisterService {
    fun register(command: SellerRegisterCommand)
}
