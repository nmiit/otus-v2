package com.example.test_kuber.config

import com.example.test_kuber.security.AuthenticationFilter
import com.example.test_kuber.security.manager.request_manager.CasAuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Profile("!security_disabled")
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
class WebSecurityConfig(private val casAuthManager: CasAuthenticationManager) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain =
        with(http) {
            csrf { csrf -> csrf.disable() }
            addFilterAfter(
                AuthenticationFilter(casAuthManager),
                UsernamePasswordAuthenticationFilter::class.java
            )
                .headers {
                    headers ->
                    headers.cacheControl { }
                    headers.frameOptions { it.disable() }
                }
                .sessionManagement {
                    sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .build()
        }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(
                    "/actuator/**",
                    "/swagger*",
                    "/swagger*/**",
                    "/webjars/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**"
                )
        }
    }
}