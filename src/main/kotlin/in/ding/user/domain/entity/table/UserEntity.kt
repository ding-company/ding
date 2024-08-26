package `in`.ding.user.domain.entity.table

import `in`.ding.common.BaseEntity
import `in`.ding.user.domain.entity.enumerate.UserNationality
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Column(unique = true, length = 36)
    val externalKey: String,

    @Column(unique = true, length = 20)
    val phoneNumber: String? = null,

    @Column(unique = true, length = 40)
    val email: String? = null,

    @Column(length = 40)
    val name: String? = null,

    @Column(length = 40)
    val nationality: UserNationality? = UserNationality.KR

) : BaseEntity()
