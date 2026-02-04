package `in`.ding.common.infra.http

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class ClientPlatformResolver {

    // TODO null시 logging
    fun resolve(request: HttpServletRequest): ClientPlatform? {
        val ua = request.getHeader("User-Agent") ?: ""
        return when {
            ua.contains("iPhone", true) -> ClientPlatform.IOS
            ua.contains("Android", true) -> ClientPlatform.ANDROID
            ua.contains("Web", true) -> ClientPlatform.WEB
            ua.contains("Pos", true) -> ClientPlatform.POS
            ua.contains("Kiosk", true) -> ClientPlatform.KIOSK
            else -> null
        }
    }
}
