package `in`.ding.user.term.infrastructure.db.table

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ContentSnapshotConverter(
    private val objectMapper: ObjectMapper
) : AttributeConverter<ContentSnapshot, String> {

    override fun convertToDatabaseColumn(attribute: ContentSnapshot?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): ContentSnapshot? {
        return dbData?.let { objectMapper.readValue(it, ContentSnapshot::class.java) }
    }
}
