package com.dmonti.infra.http.security

import com.dmonti.domain.entity.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val account: Account
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return account.roles.map { GrantedAuthority { it.name.name } }
    }

    override fun getPassword(): String? {
        return account.password?.value
    }

    override fun getUsername(): String {
        return account.email
    }
}