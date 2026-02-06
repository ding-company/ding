package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.common.domain.DomainID
import `in`.ding.customer.domain.model.enumerate.CustomerStatus
import `in`.ding.customer.infrastructure.db.table.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerJpaRepository : JpaRepository<CustomerEntity, DomainID> {
    fun findByPhoneNumberAndStatusAndIsDeleted(
        phoneNumber: String,
        status: CustomerStatus,
        isDeleted: Boolean
    ): CustomerEntity
}
