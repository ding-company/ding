package `in`.ding.common.log

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> logger(): Logger =
    LoggerFactory.getLogger(T::class.java)

object LogObjectMapper {
    val mapper: ObjectMapper = ObjectMapper().apply {
        setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
    }
}

fun Logger.infoJson(map: Map<String, Any>) {
    this.info(LogObjectMapper.mapper.writeValueAsString(map))
}

fun Logger.errorJson(map: Map<String, Any>) {
    this.error(LogObjectMapper.mapper.writeValueAsString(map))
}
fun ByteArray?.toJsonLog(): Any {
    if (this == null || this.isEmpty()) return ""
    return try {
        LogObjectMapper.mapper.readValue(this, Any::class.java)
    } catch (e: JsonParseException) {
        String(this, Charsets.UTF_8)
    }
}
