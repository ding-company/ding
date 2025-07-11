package `in`.ding.user.term.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.user.term.domain.model.enumerate.TermTitle
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "terms")
class TermEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val title: TermTitle,

    @Column(length = 5000)
    val content: String,

    @Column()
    val isRequired: Boolean,

    @Column()
    val version: Int,

    @Column()
    val effectiveFrom: LocalDateTime,
    @Column()
    val effectiveTo: LocalDateTime? = null,
    @Column()
    val defaultAgreementValidityPeriod: Duration? = null
) : SoftDeletedBaseEntity()
