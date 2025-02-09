package com.dmonti.application.repository

import com.dmonti.domain.entity.Account

interface AccountRepository {
    fun save(entity: Account): Account
    fun findByEmail(email: String): Account?
    fun existsByEmail(email: String): Boolean
}