package com.example.test_kuber.persistence.entity

import com.example.test_kuber.persistence.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToMany

@Entity
class CasRole(
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    val cas: List<Cas> = mutableListOf(),

    @Column(name = "role_name", unique = true)
    val roleName: String
): BaseEntity()