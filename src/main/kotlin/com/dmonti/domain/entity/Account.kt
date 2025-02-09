package com.dmonti.domain.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.GenerationType.SEQUENCE
import java.time.Instant
import java.time.Instant.now

@Entity
@Table(indexes = [Index(name = "idx_account_email", columnList = "email", unique = true)])
class Account(
    @Column(nullable = false)
    var email: String,
    @OneToOne(mappedBy = "account", cascade = [ALL])
    var password: AccountPassword? = null,
    @ManyToMany
    val roles: MutableSet<Role> = mutableSetOf(),
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 1)
    val id: Long? = null,
    @Column(nullable = false)
    val createdAt: Instant = now(),
) {
    fun setPassword(value: String) {
        if (password == null) {
            password = AccountPassword(this, value)
        } else {
            password!!.value = value
        }
    }
}