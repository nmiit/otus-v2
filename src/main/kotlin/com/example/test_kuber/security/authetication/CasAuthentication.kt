package com.example.test_kuber.security.authetication

import com.example.test_kuber.dto.CasInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class CasAuthentication(
    private val casId: Long? = null,
    private val sub: String? = null,
    private val roles: MutableCollection<SimpleGrantedAuthority> = mutableListOf()
): Authentication {

    constructor(casInfo: CasInfo): this(
        casId = casInfo.casId,
        sub = casInfo.sub,
        roles = casInfo.roles?.map { SimpleGrantedAuthority(it) }?.toMutableList()
            ?: mutableListOf()
    )

    override fun getName(): String = "Cas=$sub"

    override fun getAuthorities(): Collection<GrantedAuthority?> = roles

    override fun getCredentials(): Any = throw UnsupportedOperationException()

    override fun getDetails(): Any = "Cas=$sub"

    override fun getPrincipal(): Long? = casId

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) = throw UnsupportedOperationException()

    override fun toString(): String = "CasAuthentication(casId=$casId, sub=$sub roles=$roles)"
}