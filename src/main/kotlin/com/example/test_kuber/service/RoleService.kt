package com.example.test_kuber.service

import com.example.test_kuber.persistence.entity.CasRole
import com.example.test_kuber.persistence.repository.CasRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService(
    private val roleRepository: CasRoleRepository
) {
    @Transactional
    fun createRole(roleName: String): CasRole {
        val casRole = CasRole(roleName = roleName)
        return roleRepository.save(casRole)
    }

    @Transactional(readOnly = true)
    fun getRoleByName(roleName: String) : CasRole? = roleRepository.findByRoleName(roleName)
}