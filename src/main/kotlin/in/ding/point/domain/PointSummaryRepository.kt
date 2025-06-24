package `in`.ding.point.domain

import `in`.ding.point.domain.model.PointSummary
import org.springframework.stereotype.Repository

@Repository
interface PointSummaryRepository {
    fun save(point: PointSummary): PointSummary
}
