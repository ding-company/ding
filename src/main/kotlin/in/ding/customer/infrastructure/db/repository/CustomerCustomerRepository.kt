package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.customer.domain.entity.table.CustomerEntity

interface CustomerCustomerRepository {
    fun save(): CustomerEntity
}
