package com.dmonti.domain.entity

import com.dmonti.domain.entity.Account
import jakarta.persistence.*
import jakarta.persistence.GenerationType.SEQUENCE
import java.time.Instant
import java.time.Instant.now

@Entity
@Table(indexes = [Index(name = "idx_password_account", columnList = "account_id", unique = true)])
class AccountPassword(
    @OneToOne(optional = false)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_password_account"))
    val account: Account,
    @Column(columnDefinition = "text", nullable = false)
    var value: String,
    var blocked: Boolean = false,
    var lastAuthentication: Instant? = null,
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "person_password_generator")
    @SequenceGenerator(name = "person_password_generator", sequenceName = "person_password_seq", allocationSize = 1)
    val id: Long? = null,
    @Column(nullable = false)
    val createdAt: Instant = now(),
)