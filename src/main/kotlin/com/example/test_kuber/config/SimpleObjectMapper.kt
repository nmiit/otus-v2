package com.example.test_kuber.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class SimpleObjectMapper: ObjectMapper() {

    init { configureObjectMapper(this) }

    companion object {
        fun configureObjectMapper(objectMapper: ObjectMapper) =
            objectMapper.apply {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                registerKotlinModule()
                    .registerModule(JavaTimeModule())
                    .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
            }
    }
}