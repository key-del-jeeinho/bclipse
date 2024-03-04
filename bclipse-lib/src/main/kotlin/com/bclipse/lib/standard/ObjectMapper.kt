package com.bclipse.lib.standard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object ObjectMapper: ObjectMapper() {
    init {
        registerKotlinModule()
        registerModule(JavaTimeModule())
    }
}