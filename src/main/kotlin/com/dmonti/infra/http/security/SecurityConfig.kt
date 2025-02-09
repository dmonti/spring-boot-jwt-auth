package com.dmonti.infra.http.security

import com.dmonti.infra.http.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val jwtAuthenticationFilter: JwtAuthenticationFilter) {
    companion object {
        val PUBLIC_ENDPOINTS: Array<String> = arrayOf(
            "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**",
            "/account/sign-up", "/account/sign-in"
        )
    }

    @Bean
    fun securityFilterChain(security: HttpSecurity): SecurityFilterChain? {
        return security //
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(*PUBLIC_ENDPOINTS).permitAll().anyRequest().permitAll()
            }.sessionManagement { it.sessionCreationPolicy(STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java).build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}