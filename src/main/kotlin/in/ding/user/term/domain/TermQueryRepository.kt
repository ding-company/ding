package `in`.ding.user.term.domain

import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TermQueryRepository {
    fun findRequiredTermsNotAgreedBy(userExKey: UUID): List<RequiredTermForm>
}
