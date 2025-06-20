package `in`.ding.customer.domain

import `in`.ding.common.DomainID
import `in`.ding.common.ErrorMessage
import `in`.ding.customer.domain.model.Customer
import `in`.ding.customer.infrastructure.db.table.CustomerEntity
import org.springframework.stereotype.Component

@Component
class CustomerMapper {
    fun toDomain(entity: CustomerEntity): Customer {
        return Customer(
            id = DomainID(requireNotNull(entity.id) { ErrorMessage.ID_IS_NULL }),
            exKey = entity.exKey,
            name = entity.name,
            phoneNumber = entity.phoneNumber,
            sellerExKey = entity.sellerExKey,
            userExKey = entity.userExKey,
            registeredAt = entity.registeredAt
        )
    }

    fun toEntity(domain: Customer): CustomerEntity {
        val entity = CustomerEntity(
            exKey = domain.exKey,
            name = domain.name,
            sellerExKey = domain.sellerExKey,
            userExKey = domain.userExKey,
            phoneNumber = domain.phoneNumber,
            registeredAt = domain.registeredAt
        )
        if (domain.id.isAssigned()) {
            entity.id = domain.id.value
        }
        return entity
    }
}
