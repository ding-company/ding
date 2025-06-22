package `in`.ding.customer.domain

import `in`.ding.customer.domain.model.Customer
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository {
    fun save(domain: Customer): Customer
    fun findByPhoneNumber(phoneNumber: String): Customer?
}
