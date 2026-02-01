package `in`.ding.seller.domain.exception

import `in`.ding.common.infra.http.exception.BadRequestException

class DuplicatedSellerNameException : BadRequestException(message = "이미 등록된 셀러 이름 입니다.")
