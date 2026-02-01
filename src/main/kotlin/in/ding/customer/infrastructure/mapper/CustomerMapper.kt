package `in`.ding.customer.infrastructure.mapper

import `in`.ding.common.infra.http.ErrorMessage
import `in`.ding.customer.domain.model.Customer
import `in`.ding.customer.domain.model.CustomerID
import `in`.ding.customer.infrastructure.db.table.CustomerEntity
import org.springframework.stereotype.Component

@Component
class CustomerMapper {
    fun toDomain(entity: CustomerEntity): Customer {
        return Customer(
            id = CustomerID(requireNotNull(entity.id) { ErrorMessage.ID_IS_NULL }),
            exKey = entity.exKey,
            name = entity.name,
            phoneNumber = entity.phoneNumber,
            sellerExKey = entity.sellerExKey,
            userExKey = entity.userExKey,
            registeredAt = entity.registeredAt,
            status = entity.status
        )
    }

    fun toEntity(domain: Customer): CustomerEntity {
        val entity = CustomerEntity(
            exKey = domain.exKey,
            name = domain.name,
            sellerExKey = domain.sellerExKey,
            userExKey = domain.userExKey,
            phoneNumber = domain.phoneNumber,
            registeredAt = domain.registeredAt,
            status = domain.status,
        )
        if (domain.id.isAssigned()) {
            entity.id = domain.id.value
        }
        return entity
    }
}
