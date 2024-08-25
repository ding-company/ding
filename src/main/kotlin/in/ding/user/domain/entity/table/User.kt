package `in`.ding.user.domain.entity.table

import `in`.ding.user.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Column(unique = true, length = 36)
    val externalKey: String,

    @Column(unique = true, length = 20)
    val phoneNumber: String? = null,

    @Column(unique = true, length = 40)
    val email: String? = null,
    @Column()
    val name: String,

) : BaseEntity()
