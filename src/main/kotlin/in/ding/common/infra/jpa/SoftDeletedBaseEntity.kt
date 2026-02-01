package `in`.ding.common.infra.jpa

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class SoftDeletedBaseEntity : BaseEntity() {
    @Column()
    open var isDeleted: Boolean = false

    @Column()
    open var deletedAt: LocalDateTime? = null
}
