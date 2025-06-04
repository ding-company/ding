package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.customer.domain.model.table.CustomerEntity

interface CustomerCustomerRepository {
    fun save(): CustomerEntity
}
