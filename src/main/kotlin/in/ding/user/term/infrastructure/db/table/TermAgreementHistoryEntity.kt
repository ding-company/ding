package `in`.ding.user.term.infrastructure.db.table

import `in`.ding.common.SoftDeletedBaseEntity
import `in`.ding.user.term.domain.model.enumerate.TermAgreementStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "term_agreement_history")
class TermAgreementHistoryEntity(
    @Column(unique = true, length = 36)
    val exKey: UUID,

    @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    val term: TermEntity,

    @Column(length = 5000)
    val userExKey: UUID,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val status: TermAgreementStatus,
    @Column
    val eventAt: LocalDateTime = LocalDateTime.now(),

    @Column()
    val version: Int,

) : SoftDeletedBaseEntity()
