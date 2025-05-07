package `in`.ding.customer.domain.service

import `in`.ding.customer.domain.service.dto.CustomerRegisterDTO
import `in`.ding.customer.infrastructure.db.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerCommandService(repository: CustomerRepository) {
    fun register(dto: CustomerRegisterDTO) {
    }
}
