package com.example.test_kuber.web

import com.example.test_kuber.dto.CasProfileDto
import com.example.test_kuber.service.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val profileService: ProfileService
) {

    @GetMapping
    fun getProfile(): ResponseEntity<CasProfileDto> {
        val casId = SecurityContextHolder.getContext().authentication.principal as Long

        val profile = profileService.findByCasId(casId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(profile)
    }

    @PutMapping
    fun updateProfile(
        @RequestBody updateRequest: CasProfileDto
    ): ResponseEntity<CasProfileDto> {
        val casId = SecurityContextHolder.getContext().authentication.principal as Long

        val updated = profileService.update(casId, updateRequest)
        return ResponseEntity.ok(updated)
    }
}
