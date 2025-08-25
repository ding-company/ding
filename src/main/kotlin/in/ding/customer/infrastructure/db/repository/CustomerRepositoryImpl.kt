package `in`.ding.customer.infrastructure.db.repository

import `in`.ding.customer.domain.CustomerRepository
import `in`.ding.customer.domain.model.Customer
import `in`.ding.customer.domain.model.enumerate.CustomerStatus
import `in`.ding.customer.infrastructure.mapper.CustomerMapper
import org.springframework.stereotype.Repository

@Repository
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
    override fun findByPhoneNumber(phoneNumber: String): Customer? {
        val entity = jpaRepository.findByPhoneNumberAndStatusAndIsDeleted(phoneNumber, CustomerStatus.REGISTERED, false)
        return mapper.toDomain(entity)
    }
}
