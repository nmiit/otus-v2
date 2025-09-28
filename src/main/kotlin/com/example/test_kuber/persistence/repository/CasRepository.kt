package com.example.test_kuber.persistence.repository

import com.example.test_kuber.persistence.entity.Cas
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CasRepository: JpaRepository<Cas, Long> {
    fun findByName(name: String): Cas?
}