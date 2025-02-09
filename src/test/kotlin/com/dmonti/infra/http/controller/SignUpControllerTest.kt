package com.dmonti.infra.http.controller

import com.dmonti.JwtExampleApplication
import com.dmonti.application.repository.AccountRepository
import com.dmonti.application.usecase.CreateAccountPassword
import com.dmonti.domain.entity.Account
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.util.UUID.randomUUID

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [JwtExampleApplication::class])
class SignUpControllerTest(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val accountRepository: AccountRepository
) {
    companion object {
        const val ENDPOINT = "/account/sign-up"
        fun randomEmail(): String = "${randomUUID()}@email.com"
    }

    @Test
    fun `Assert sign-up success`() {
        // Given
        val input = CreateAccountPassword.Input(randomEmail(), "12345678")
        val request = HttpEntity<CreateAccountPassword.Input>(input)
        // When
        val entity = restTemplate.postForEntity<CreateAccountPassword.Output>(ENDPOINT, request)
        // Then
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body?.success).isTrue()
    }

    @Test
    fun `Assert sign-up email already exists`() {
        // Given
        val email = randomEmail()
        val input = CreateAccountPassword.Input(email, "12345678")
        val request = HttpEntity<CreateAccountPassword.Input>(input)
        // When
        accountRepository.save(Account(email))
        val entity = restTemplate.postForEntity<CreateAccountPassword.Output>(ENDPOINT, request)
        // Then
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body?.success).isFalse()
    }

    @Test
    fun `Assert sign-up bad email request`() {
        // Given
        val input = CreateAccountPassword.Input("invalid-email", "12345678")
        val request = HttpEntity<CreateAccountPassword.Input>(input)
        // When
        val entity = restTemplate.postForEntity<CreateAccountPassword.Output>(ENDPOINT, request)
        // Then
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `Assert sign-up bad password request`() {
        // Given
        val input = CreateAccountPassword.Input(randomEmail(), "123")
        val request = HttpEntity<CreateAccountPassword.Input>(input)
        // When
        val entity = restTemplate.postForEntity<CreateAccountPassword.Output>(ENDPOINT, request)
        // Then
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}