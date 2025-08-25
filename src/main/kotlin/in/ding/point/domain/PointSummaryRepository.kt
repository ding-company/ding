package `in`.ding.point.domain

import `in`.ding.point.domain.model.PointSummary

interface PointSummaryRepository {
    fun save(point: PointSummary): PointSummary
}
