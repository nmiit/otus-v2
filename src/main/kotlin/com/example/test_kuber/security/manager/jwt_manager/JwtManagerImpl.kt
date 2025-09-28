package com.example.test_kuber.security.manager.jwt_manager

import com.example.test_kuber.config.SimpleObjectMapper
import com.example.test_kuber.dto.CasInfo
import com.fasterxml.jackson.module.kotlin.convertValue
import io.jsonwebtoken.JwtParser
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!security_disabled")
@Component
class JwtManagerImpl(
    private val objectMapper: SimpleObjectMapper,
    private val jwtParser: JwtParser
) : JwtManager {

    override fun decodeCasInfo(jwt: String): CasInfo {
        val jwtWithoutBearer = jwt.substring(jwt.indexOf(' ') + 1)
        return jwtParser.parseClaimsJws(jwtWithoutBearer)
            .body
            .let { objectMapper.convertValue(it) }
    }
}