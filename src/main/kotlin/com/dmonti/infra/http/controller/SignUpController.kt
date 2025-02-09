package com.dmonti.infra.http.controller

import com.dmonti.application.usecase.CreateAccountPassword
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/sign-up")
class SignUpController(val createAccountPassword: CreateAccountPassword) {
    @PostMapping
    fun signup(@Valid @RequestBody input: CreateAccountPassword.Input): ResponseEntity<CreateAccountPassword.Output> {
        val output = createAccountPassword.execute(input)
        return ResponseEntity.ok(output)
    }
}