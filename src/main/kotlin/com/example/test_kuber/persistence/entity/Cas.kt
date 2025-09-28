package com.example.test_kuber.persistence.entity

import com.example.test_kuber.dto.CasProfileDto
import com.example.test_kuber.dto.CasRoleDto
import com.example.test_kuber.persistence.entity.base.BaseAuditEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToOne

@Entity
class Cas(
    @Column(unique = true, nullable = false)
    var name: String,

    @Column(nullable = false)
    val password: String,  // хэшированный

    @Column(unique = true)
    var email: String? = null,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var roles: MutableList<CasRole> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "profile_id", unique = true)
    var casProfile: CasProfile? = null
): BaseAuditEntity() {

    fun toCasProfileDto(): CasProfileDto =
        CasProfileDto(
            casId = this.id,
            name = this.name,
            email = this.email,
            fullName = this.casProfile?.fullName,
            phone = this.casProfile?.phone,
            city = this.casProfile?.address?.city,
            street = this.casProfile?.address?.street,
            houseNumber = this.casProfile?.address?.house
        )

    fun toCasRolesDto(): CasRoleDto =
        CasRoleDto(
            casId = this.id,
            name = this.name,
            roles = this.roles.map { it.roleName }
        )
}