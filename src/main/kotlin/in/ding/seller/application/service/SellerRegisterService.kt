package `in`.ding.seller.application.service

import `in`.ding.seller.application.dto.SellerRegisterCommand
import org.springframework.stereotype.Service

@Service
interface SellerRegisterService {
    fun register(command: SellerRegisterCommand)
}
