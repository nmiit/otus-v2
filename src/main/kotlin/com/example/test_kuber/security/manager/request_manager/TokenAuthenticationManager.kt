package com.example.test_kuber.security.manager.request_manager

import org.springframework.security.core.Authentication

interface TokenAuthenticationManager {
    fun authenticateByToken(token: String): Authentication
    fun getAuthHeader(): String
}