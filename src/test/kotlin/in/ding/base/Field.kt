package `in`.ding.base

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

fun field(
    path: String,
    description: String
): FieldDescriptor =
    fieldWithPath(path).description(description)

fun optionalField(
    path: String,
    description: String
): FieldDescriptor =
    fieldWithPath(path).optional().description(description)
