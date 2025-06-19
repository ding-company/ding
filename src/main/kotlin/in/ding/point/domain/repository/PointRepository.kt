package `in`.ding.point.domain.repository

import `in`.ding.point.domain.entity.Point
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PointRepository {
    fun save(point: Point): Point
    fun findByCustomerExKey(customerExKey: UUID): Point?
}
