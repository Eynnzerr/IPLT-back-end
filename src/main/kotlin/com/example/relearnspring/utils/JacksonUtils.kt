package com.example.relearnspring.utils

import com.fasterxml.jackson.databind.ObjectMapper

object JacksonUtils {
    private val objectMapper by lazy {
        ObjectMapper()
    }

    fun toJson(obj: Any) = objectMapper.writeValueAsString(obj)
}