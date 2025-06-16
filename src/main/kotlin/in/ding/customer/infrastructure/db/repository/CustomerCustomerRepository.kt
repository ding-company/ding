package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.customer.infrastructure.db.table.CustomerEntity

interface CustomerCustomerRepository {
    fun save(): CustomerEntity
}
