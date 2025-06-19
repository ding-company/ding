package `in`.ding.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
abstract class SoftDeletedBaseEntity : BaseEntity() {
    @Column()
    open var isDeleted: Boolean = false

    @Column()
    open var deletedAt: LocalDateTime? = null
}
