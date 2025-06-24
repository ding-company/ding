package `in`.ding.point.application.handler

import `in`.ding.point.domain.PointSummaryRepository
import `in`.ding.point.domain.PointWalletRepository
import `in`.ding.point.domain.model.PointSummary
import `in`.ding.point.domain.model.PointWallet
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PointInitHandler(
    private val pointWalletRepository: PointWalletRepository,
    private val pointSummaryRepository: PointSummaryRepository
) {
    fun initPoint(customerExKey: UUID) {
        val wallet = PointWallet.init(customerExKey)
        val summary = PointSummary.create(customerExKey)

        pointWalletRepository.save(wallet)
        pointSummaryRepository.save(summary)
    }
}
