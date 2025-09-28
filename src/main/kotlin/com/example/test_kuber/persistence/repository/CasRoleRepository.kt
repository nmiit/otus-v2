package com.example.test_kuber.persistence.repository

import com.example.test_kuber.persistence.entity.CasRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CasRoleRepository: JpaRepository<CasRole, Long> {
    fun findByRoleName(roleName: String): CasRole?
}