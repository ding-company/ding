package `in`.ding.user.term.infrastructure.db.table

import `in`.ding.common.infra.jpa.SoftDeletedBaseEntity
import `in`.ding.user.domain.enumerate.UserNationality
import `in`.ding.user.term.domain.model.enumerate.PrincipalType
import `in`.ding.user.term.domain.model.enumerate.ServiceChannel
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "term_conditions")
class TermConditionEntity(

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val principalType: PrincipalType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val serviceChannel: ServiceChannel,

    @Column(length = 3)
    val country: UserNationality?,

    @Column(nullable = false)
    val isRequired: Boolean,
) : SoftDeletedBaseEntity()
