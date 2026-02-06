package `in`.ding.common.infra.http

import `in`.ding.common.infra.infraexception.MissedHeaderException
import `in`.ding.common.infra.infraexception.NotAllowedClientPlatform
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class ClientPlatformResolver {

    fun resolve(request: HttpServletRequest): ClientPlatform? {
        val headerName = "X-Client-Platform"
        val ua = request.getHeader(headerName) ?: throw MissedHeaderException(headerName)
        return when {
            ua.contains("iPhone", true) -> ClientPlatform.IOS
            ua.contains("Android", true) -> ClientPlatform.ANDROID
            ua.contains("Web", true) -> ClientPlatform.WEB
            ua.contains("Pos", true) -> ClientPlatform.POS
            ua.contains("Kiosk", true) -> ClientPlatform.KIOSK
            else -> throw NotAllowedClientPlatform()
        }
    }
}
