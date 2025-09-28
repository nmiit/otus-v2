package com.example.test_kuber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder.getContext
import java.util.Optional

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaConfig {

    @Bean
    fun auditorProvider(): AuditorAware<String> =
        AuditorAware { Optional.of(getAuthenticationIdentityFromContext()) }
}

fun getAuthenticationIdentityFromContext(): String =
    getContext().authentication?.name ?: ""