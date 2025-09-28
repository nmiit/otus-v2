package com.example.test_kuber.security.manager.jwt_manager

import com.example.test_kuber.dto.CasInfo

interface JwtManager {
    fun decodeCasInfo(jwt: String): CasInfo
}