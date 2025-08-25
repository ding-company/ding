package `in`.ding.customer.domain

import `in`.ding.customer.domain.model.Customer

interface CustomerRepository {
    fun save(domain: Customer): Customer
    fun findByPhoneNumber(phoneNumber: String): Customer?
}
