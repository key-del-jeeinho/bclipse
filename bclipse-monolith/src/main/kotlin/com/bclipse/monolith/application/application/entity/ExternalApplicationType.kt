package com.bclipse.monolith.application.application.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class ExternalApplicationType(
    @get:JsonValue
    val type: String,
) {
    TOSS("toss"),;

    companion object {
        @JsonCreator
        fun of(value: String): ExternalApplicationType? =
            entries.firstOrNull { it.type == value }
    }
}