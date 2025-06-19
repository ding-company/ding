package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.common.DomainID
import `in`.ding.customer.infrastructure.db.table.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerJpaRepository : JpaRepository<CustomerEntity, DomainID>
