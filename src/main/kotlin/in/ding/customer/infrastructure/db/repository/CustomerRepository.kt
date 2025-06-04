package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.customer.domain.model.table.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerEntity, Long>, CustomerCustomerRepository
