package com.example.test_kuber.exception

class UserNotFountException(override val message: String? = null): RuntimeException(message)

class RoleNotFountException(override val message: String? = null): RuntimeException(message)