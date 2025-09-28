package com.example.test_kuber.service

import com.example.test_kuber.dto.CasProfileDto
import com.example.test_kuber.persistence.entity.Address
import com.example.test_kuber.persistence.entity.CasProfile
import com.example.test_kuber.persistence.repository.CasRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val casRepository: CasRepository,
) {
    @Transactional(readOnly = true)
    fun findByCasId(casId: Long): CasProfileDto? =
        casRepository.findById(casId).orElse(null)?.toCasProfileDto()

    @Transactional
    fun update(casId: Long, dto: CasProfileDto): CasProfileDto {
        val cas = casRepository.findById(casId).orElse(null)
        dto.name?.let { cas.name = it }
        dto.email?.let { cas.email = it }

        val casProfile = cas.casProfile ?: CasProfile(cas = cas)
        dto.fullName?.let { casProfile.fullName = it }
        dto.phone?.let { casProfile.phone = it }

        val address = cas.casProfile?.address ?: Address(casProfile = casProfile)
        dto.city?.let { address.city = it }
        dto.street?.let { address.street = it }
        dto.houseNumber?.let { address.house = it }

        cas.casProfile = casProfile
        casProfile.address = address

        return casRepository.save(cas).toCasProfileDto()
    }
}