package com.dmonti.infra.http.controller

import com.dmonti.application.usecase.ValidateAccountPassword
import com.dmonti.infra.http.jwt.JwtService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/sign-in")
class SignInController(val validateAccountPassword: ValidateAccountPassword, val jwtService: JwtService) {
    @PostMapping
    fun signup(@Valid @RequestBody input: ValidateAccountPassword.Input): ResponseEntity<ValidateAccountPassword.Output> {
        val output = validateAccountPassword.execute(input)
        if (!output.validated) {
            return ResponseEntity.ok().build()
        }
        val jwt = jwtService.createToken(input.email!!)
        return ResponseEntity.ok() //
            .header("Authorization", "Bearer $jwt").build()
    }
}