package `in`.ding.base

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper

object RestdocMockMvc {

    fun document(
        identifier: String,
        summary: String,
        description: String
    ) =
        MockMvcRestDocumentationWrapper.document(
            identifier = identifier,
            description = description,
            summary = summary
        )
}
