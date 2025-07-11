package `in`.ding.user.auth.domain.exception

object ErrorMessage {
    const val WRONG_OTP_CODE = "틀린 otp 코드 입니다."
    const val OTP_NOT_FOUND = "Not Found Otp"
    const val EXPIRED_OTB = "만료된 otp 코드 입니다."
    const val TRIED_OVER_THE_5_TIMES = "otp인증을 5회 이상 실패하였습니다."
    const val BLACKLIST = "OTP 인증이 차단되었습니다."
}
