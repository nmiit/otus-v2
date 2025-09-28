package com.example.test_kuber.security.manager.request_manager

import com.example.test_kuber.security.authetication.CasAuthentication
import com.example.test_kuber.security.manager.jwt_manager.JwtManager
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Profile("!security_disabled")
@Component
class CasAuthenticationManager(private val jwtManager: JwtManager): TokenAuthenticationManager {

    override fun authenticateByToken(token: String): Authentication =
        try {
            val casInfo = jwtManager.decodeCasInfo(token)
            LOG.info { "CasInfo: $casInfo" }
            casInfo
        } catch (e: RuntimeException) {
            throw BadCredentialsException("Cannot decode jwt for cas user.\n${e.message}")
        }
            .let { CasAuthentication(it) }

    override fun getAuthHeader(): String = CAS_HEADER

    companion object{
        const val CAS_HEADER = "X-AUTH-TOKEN"
        private val LOG = KotlinLogging.logger {  }
    }
}