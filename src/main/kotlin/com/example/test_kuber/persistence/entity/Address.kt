package com.example.test_kuber.persistence.entity

import com.example.test_kuber.persistence.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne

@Entity
class Address(
    @Column(nullable = false)
    var city: String = "",
    @Column(nullable = false)
    var street: String = "",
    @Column(nullable = false)
    var house: String = "",
    @OneToOne(mappedBy = "address")
    val casProfile: CasProfile
): BaseEntity()