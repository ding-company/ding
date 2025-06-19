package `in`.ding.point.application.service

import `in`.ding.point.domain.PointRepository
import `in`.ding.point.domain.entity.Point
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PointAppService(private val repository: PointRepository) {
    fun initPoint(customerExKey: UUID) {
        repository.findByCustomerExKey(customerExKey)
            ?: repository.save(Point(customerExKey = customerExKey))
    }
}
