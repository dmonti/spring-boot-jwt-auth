package com.dmonti.application.usecase

import com.dmonti.application.repository.AccountRepository
import com.dmonti.domain.entity.Account
import jakarta.transaction.Transactional
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Transactional
class CreateAccountPassword(
    val accountRepository: AccountRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun execute(input: Input): Output {
        val email = input.email!!
        if (accountRepository.existsByEmail(email)) {
            val duration = (2_500..5_000).random()
            Thread.sleep(duration.toLong())
            return Output(false, "Email already exists")
        }

        val account = Account(email)
        val password = passwordEncoder.encode(input.password!!)
        account.setPassword(password)
        accountRepository.save(account)

        return Output(true)
    }

    data class Input(
        @field:NotBlank @field:Email val email: String?,
        @field:NotBlank @field:Length(min = 8, max = 32) val password: String?,
    )

    data class Output(val success: Boolean, val error: String? = null)
}