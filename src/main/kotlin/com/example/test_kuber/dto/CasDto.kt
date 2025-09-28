package com.example.test_kuber.dto

class CasInfo(
    val casId: Long,
    val sub: String,
    val roles: List<String>? = null
)

class CasProfileDto(
    val name: String? = null,
    val email: String? = null,
    var fullName: String? = null,
    var phone: String? = null,
    val city: String? = null,
    val street: String? = null,
    val houseNumber: String? = null
)

class CasRoleDto(
    val casId: Long?,
    val name: String,
    val roles: List<String>
)