package `in`.ding.user.term.infrastructure.db.table

import `in`.ding.user.term.domain.model.enumerate.AppType
import `in`.ding.user.term.domain.model.enumerate.UserType
import `in`.ding.user.user.domain.model.enumerate.UserNationality
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "term_conditions")
class TermConditionEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    val term: TermEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val userType: UserType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val appType: AppType,

    @Column(length = 3)
    val country: UserNationality?,

    @Column(nullable = false)
    val isRequired: Boolean,
)
