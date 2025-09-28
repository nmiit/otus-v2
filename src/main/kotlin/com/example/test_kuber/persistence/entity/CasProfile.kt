package com.example.test_kuber.persistence.entity

import com.example.test_kuber.persistence.entity.base.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
data class CasProfile(
    @OneToOne(mappedBy = "casProfile")
    val cas: Cas,

    @Column(unique = true)
    var fullName: String? = null,

    @Column(unique = true)
    var phone: String? = null,

    @OneToOne(cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JoinColumn(name = "address_id", unique = true)
    var address: Address? = null
): BaseEntity()