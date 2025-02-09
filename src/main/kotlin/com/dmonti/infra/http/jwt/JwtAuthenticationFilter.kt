package com.dmonti.infra.http.jwt

import com.dmonti.infra.http.security.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    val jwtService: JwtService,
    val userDetailsService: UserDetailsServiceImpl,
    @Value("\${server.servlet.context-path}") val contextPath: String
) : OncePerRequestFilter() {
    companion object {
        const val BEARER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader("Authorization")
        if (authorization == null || authenticate(authorization)) {
            filterChain.doFilter(request, response)
        } else {
            response.sendError(SC_UNAUTHORIZED, "Unauthorized")
        }
    }

    private fun authenticate(authorization: String): Boolean {
        val token = authorization.substring(BEARER_PREFIX.length)
        val jwt = jwtService.decodeToken(token)
        val userDetails = userDetailsService.loadUserByUsername(jwt.subject)
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(userDetails.username, null, userDetails.authorities)
        return true
    }
}