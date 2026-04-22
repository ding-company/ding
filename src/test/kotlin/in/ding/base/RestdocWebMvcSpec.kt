package `in`.ding.base

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

// 컨트롤러 테스트
@WebMvcTest
@AutoConfigureRestDocs
abstract class RestdocWebMvcSpec(
    body: BehaviorSpec.() -> Unit = {}
) : BehaviorSpec(body) {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    protected lateinit var mockMvc: MockMvc
}
