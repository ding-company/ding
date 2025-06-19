package `in`.ding.customer.domain

import `in`.ding.customer.infrastructure.db.table.CustomerEntity
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository {
    fun save(): CustomerEntity
}
