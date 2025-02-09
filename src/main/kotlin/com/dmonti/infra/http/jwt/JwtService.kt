package com.dmonti.infra.http.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class JwtService(
    @Value("\${jwt.issuer}") val issuer: String,
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.ttl}") val duration: Duration,
) {
    val algorithm: Algorithm get() = Algorithm.HMAC256(secret)

    fun createToken(email: String): String {
        return JWT.create()
            .withIssuer(issuer)
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plus(duration))
            .withSubject(email)
            .sign(algorithm)
    }

    fun decodeToken(token: String): DecodedJWT {
        return JWT.require(algorithm)
            .withIssuer(issuer)
            .build()
            .verify(token)
    }
}