package com.example.test_kuber.config

import com.example.test_kuber.security.utils.JwtUtils
import io.jsonwebtoken.JwtParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import io.jsonwebtoken.Jwts

@Configuration
@Profile("!security_disabled")
class JwtConfig(private val jwtUtils: JwtUtils) {

    @Value("\${jwt.rsa-public-key}")
    private lateinit var jwtPublicKey: String

    @Bean
    fun jwtParser(): JwtParser = jwtUtils.convertKeyToRSAPublicKey(jwtPublicKey)
        .let { Jwts.parserBuilder().setSigningKey(it).build() }
}