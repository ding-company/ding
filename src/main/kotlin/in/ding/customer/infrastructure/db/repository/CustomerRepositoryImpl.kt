package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.customer.domain.CustomerMapper
import `in`.ding.customer.domain.CustomerRepository
import `in`.ding.customer.domain.model.Customer

class CustomerRepositoryImpl(
    private val jpaRepository: CustomerJpaRepository,
    private val mapper: CustomerMapper
) : CustomerRepository {
    override fun save(domain: Customer): Customer {
        val entity = mapper.toEntity(domain).also {
            if (domain.id.isAssigned()) {
                it.id = domain.id.value
            }
        }
        val saved = jpaRepository.save(entity)
        return mapper.toDomain(saved)
    }
}
