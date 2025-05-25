package `in`.ding.point.domain.entity.enumerate

enum class PointTransactionSourceType {
    REVIEW,
    SIGNUP,
    PURCHASE_REWARD,
    PAYMENT,
    MANUAL_EARN,
    MANUAL_DEDUCT,
    REFUND_REVERSE_EARN,
    REFUND_REVERSE_USE,
    SYSTEM_ERROR,
    INTERNAL_ADJUST,
    EXPIRE,
    RELEASE_HOL
}
