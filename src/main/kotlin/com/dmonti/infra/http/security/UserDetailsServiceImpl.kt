package com.dmonti.infra.http.security

import com.dmonti.application.repository.AccountRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetailsImpl {
        val account = accountRepository.findByEmail(username)
        return account?.let { UserDetailsImpl(it) } ?: throw BadCredentialsException("Invalid username")
    }
}