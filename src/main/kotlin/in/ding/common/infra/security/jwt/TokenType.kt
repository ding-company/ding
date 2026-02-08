package `in`.ding.common.infra.security.jwt

enum class TokenType {
    OTP, // OTP 인증만 완료
    PRE_AUTH, // 유저 존재 + 약관 미동의 or 만료
    AUTHENTICATED, // 모든 조건 충족
    REFRESH
}
