package `in`.ding.user.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.user.domain.model.enumerate.UserNationality
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Column(unique = true, length = 20)
    val phoneNumber: String? = null,

    @Column(unique = true, length = 40)
    val email: String? = null,

    @Column(length = 40)
    val nationality: UserNationality? = UserNationality.KR,

    @Column()
    val registeredAt: LocalDateTime,
) : SoftDeletedBaseEntity()
