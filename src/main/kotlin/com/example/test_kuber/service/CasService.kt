package com.example.test_kuber.service

import com.example.test_kuber.dto.RoleDto
import com.example.test_kuber.exception.RoleNotFountException
import com.example.test_kuber.exception.UserNotFountException
import com.example.test_kuber.persistence.entity.Cas
import com.example.test_kuber.persistence.entity.CasRole
import com.example.test_kuber.persistence.repository.CasRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CasService(
    val casRepository: CasRepository,
    val roleService: RoleService
) {
    @Transactional
    fun save(cas: Cas): Cas = casRepository.save(cas)

    @Transactional(readOnly = true)
    fun findById(casId: Long): Cas? = casRepository.findById(casId).orElse(null)

    @Transactional(readOnly = true)
    fun findByIdAndConvertToListRoles(casId: Long): List<String> {
        val cas = casRepository.findById(casId).orElse(null)
            ?: throw UserNotFountException("User userID=$casId not found")
        return cas.roles.map { it.roleName }
    }

    @Transactional(readOnly = true)
    fun findByUsername(username: String): Cas? = casRepository.findByName(username)

    @Transactional
    fun addRoleToCas(casId: Long, roleDto: RoleDto): Cas {
        val cas = casRepository.findById(casId).orElse(null)
            ?: throw UserNotFountException("User userID=$casId not found")
        val role = roleService.getRoleByName(roleDto.roleName)
            ?: throw RoleNotFountException("Role ${roleDto.roleName} does not exist")
        cas.roles.add(role)
        return casRepository.save(cas)
    }

    @Transactional
    fun deleteRoleFromCas(casId: Long, roleDto: RoleDto): Cas {
        val cas = casRepository.findById(casId).orElse(null)
            ?: throw UserNotFountException("User userID=$casId not found")
        val role = roleService.getRoleByName(roleDto.roleName)
            ?: throw RoleNotFountException("Role ${roleDto.roleName} does not exist")
        cas.roles = cas.roles.filter { it == role } as MutableList<CasRole>
        return casRepository.save(cas)
    }
}
