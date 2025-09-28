package com.example.test_kuber.exception

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ErrorResponse(
    val status: Int,
    val success: Boolean = false,
    val errorCode: Int?,
    val errorMessage: String?,
    val data: MutableMap<String, Any> = mutableMapOf(),

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
)