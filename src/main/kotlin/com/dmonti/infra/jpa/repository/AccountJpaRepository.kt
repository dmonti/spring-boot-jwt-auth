package com.dmonti.infra.jpa.repository

import com.dmonti.application.repository.AccountRepository
import com.dmonti.domain.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountJpaRepository : AccountRepository, CrudRepository<Account, Long>