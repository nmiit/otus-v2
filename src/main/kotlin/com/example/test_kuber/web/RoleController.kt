package com.example.test_kuber.web

import com.example.test_kuber.dto.CasRoleDto
import com.example.test_kuber.dto.RoleDto
import com.example.test_kuber.service.CasService
import com.example.test_kuber.service.RoleService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/role")
class RoleController(
    private val casService: CasService,
    private val roleService: RoleService
) {
    @PostMapping
    fun createRole(@RequestBody roleDto: RoleDto): String {
        val role = roleService.getRoleByName(roleDto.roleName)
        return if (role == null) {
            roleService.createRole(roleDto.roleName).roleName
        } else { roleDto.roleName }
    }

    @GetMapping("/{cas_id}")
    fun getAllRolesByCas(
        @PathVariable("cas_id") casId: Long
    ): List<String> = casService.findByIdAndConvertToListRoles(casId)

    @PostMapping("/{cas_id}")
    fun addRoleToCas(
        @PathVariable("cas_id") casId: Long,
        @RequestBody roleDto: RoleDto
    ): CasRoleDto = casService.addRoleToCas(casId, roleDto).toCasRolesDto()

    @DeleteMapping("/{cas_id}")
    fun deleteRoleFromCas(
        @PathVariable("cas_id") casId: Long,
        @RequestBody roleDto: RoleDto
    ): CasRoleDto = casService.deleteRoleFromCas(casId, roleDto).toCasRolesDto()
}