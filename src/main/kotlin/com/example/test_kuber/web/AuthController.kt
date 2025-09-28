package com.example.test_kuber.web

import com.example.test_kuber.dto.AuthRequest
import com.example.test_kuber.dto.RegisterRequest
import com.example.test_kuber.persistence.entity.Cas
import com.example.test_kuber.security.utils.JwtUtils
import com.example.test_kuber.service.CasService
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile("!security_disabled")
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val casService: CasService,
    private val jwtUtils: JwtUtils,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<String> {
        if (casService.findByUsername(req.username) != null) {
            LOG.info("Error in register - username with name=${req.username} already exists")
            return ResponseEntity.badRequest().body("Username with name=${req.username} already exists")
        }
        val cas = Cas(
            name = req.username,
            password = passwordEncoder.encode(req.password),
            email = req.email
        )
        val savedCas = casService.save(cas)
        return ResponseEntity.ok("User with name=${savedCas.name} registered successfully")
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): ResponseEntity<Map<String, String>> {
        try {
            val auth: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(req.username, req.password)
            )
            val token = jwtUtils.generateToken(auth.name)
            return ResponseEntity.ok(mapOf("token" to token))
        } catch (e: Exception) {
            LOG.info("Login error: ${e.message}")
            return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }
    }

    companion object {
        private val LOG = KotlinLogging.logger {  }
    }
}
