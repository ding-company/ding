package `in`.ding.user.user.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import `in`.ding.user.user.domain.model.enumerate.UserStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    val nationality: UserNationality = UserNationality.KR,

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    val status: UserStatus = UserStatus.TEMPORARY,

    @Column()
    val registeredAt: LocalDateTime,
) : SoftDeletedBaseEntity()
