package `in`.ding.customer.domain

import `in`.ding.common.DomainID
import `in`.ding.common.ErrorMessage
import `in`.ding.customer.domain.model.Customer
import `in`.ding.customer.infrastructure.db.table.CustomerEntity
import org.springframework.stereotype.Component

@Component
class CustomerMapper {
    fun toDomain(customer: CustomerEntity): Customer {
        return Customer(
            id = DomainID(requireNotNull(customer.id) { ErrorMessage.ID_IS_NULL }),
            exKey = customer.exKey,
            name = customer.name,
            phoneNumber = customer.phoneNumber,
            sellerExKey = customer.sellerExKey,
            userExKey = customer.userExKey,
            registeredAt = customer.registeredAt
        )
    }

    fun toEntity(customer: Customer): CustomerEntity {
        return CustomerEntity(
            exKey = customer.exKey,
            name = customer.name,
            sellerExKey = customer.sellerExKey,
            userExKey = customer.userExKey,
            phoneNumber = customer.phoneNumber,
            registeredAt = customer.registeredAt
        )
    }
}
