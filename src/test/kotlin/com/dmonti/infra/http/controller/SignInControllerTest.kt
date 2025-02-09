package com.dmonti.infra.http.controller

import com.dmonti.JwtExampleApplication
import com.dmonti.application.usecase.CreateAccountPassword
import com.dmonti.application.usecase.ValidateAccountPassword
import com.dmonti.infra.http.controller.SignUpControllerTest.Companion.randomEmail
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

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [JwtExampleApplication::class])
class SignInControllerTest(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val createAccountPassword: CreateAccountPassword
) {
    companion object {
        const val ENDPOINT = "/account/sign-in"
    }

    @Test
    fun `Assert sign-up success`() {
        // Given
        val email = randomEmail()
        createAccountPassword.execute(CreateAccountPassword.Input(email, "12345678"))
        val input = ValidateAccountPassword.Input(email, "12345678")
        val request = HttpEntity<ValidateAccountPassword.Input>(input)
        // When
        val entity = restTemplate.postForEntity<ValidateAccountPassword.Output>(ENDPOINT, request)
        // Then
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.headers["Authorization"]).isNotNull()
    }
}