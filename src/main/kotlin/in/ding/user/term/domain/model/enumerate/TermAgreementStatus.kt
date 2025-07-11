package `in`.ding.user.term.domain.model.enumerate

enum class TermAgreementStatus {
    AGREED, // 유효한 동의
    WITHDRAWN, // 유저가 철회
    EXPIRED, // 유효기간 초과
    DELETED // 고객정보 파기
}
