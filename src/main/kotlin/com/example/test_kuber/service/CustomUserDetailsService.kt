package com.example.test_kuber.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val casService: CasService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val cas = casService.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found")

        return org.springframework.security.core.userdetails.User
            .withUsername(cas.name)
            .password(cas.password)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .authorities(emptyList<GrantedAuthority>())
            .build()
    }
}