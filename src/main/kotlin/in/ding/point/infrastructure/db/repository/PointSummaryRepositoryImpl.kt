package `in`.ding.point.infrastructure.db.repository

import `in`.ding.point.domain.PointSummaryRepository
import `in`.ding.point.domain.model.PointSummary
import org.springframework.stereotype.Repository

// todo 구현하기
@Repository
class PointSummaryRepositoryImpl : PointSummaryRepository {

    override fun save(point: PointSummary): PointSummary {
        throw NotImplementedError()
    }
}
