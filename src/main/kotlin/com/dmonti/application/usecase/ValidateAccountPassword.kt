package com.dmonti.application.usecase

import com.dmonti.application.repository.AccountRepository
import jakarta.transaction.Transactional
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@Transactional
class ValidateAccountPassword(
    val accountRepository: AccountRepository, val passwordEncoder: PasswordEncoder
) {
    fun execute(input: Input): Output {
        val account = accountRepository.findByEmail(input.email!!)
        if (passwordEncoder.matches(input.password!!, account?.password?.value)) {
            account?.password?.lastAuthentication = Instant.now()
            return Output(true)
        }
        return Output(false)
    }

    data class Input(
        @field:NotBlank @field:Email val email: String?,
        @field:NotBlank @field:Length(min = 8, max = 32) val password: String?,
    )

    data class Output(val validated: Boolean)
}