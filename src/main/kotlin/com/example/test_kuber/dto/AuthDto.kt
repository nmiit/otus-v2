package com.example.test_kuber.dto

data class AuthRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val fullName: String? = null,
    val phone: String? = null
)

data class RoleDto(
    val roleName: String
)