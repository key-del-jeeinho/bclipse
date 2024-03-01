package com.bclipse.lib.application.entity

import com.bclipse.lib.common.entity.Base64UUID
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class TossApplication(
    val id: Base64UUID,
    val clientKey: String,
    val secretKey: String,
    val version: TossApiVersion,
) {
    private val isTestKey: Boolean = clientKey.startsWith("test") && secretKey.startsWith("test")
    val environment: TossApplicationEnvironment =
        if(isTestKey) TossApplicationEnvironment.TEST
        else TossApplicationEnvironment.LIVE
}

enum class TossApplicationEnvironment {
    TEST, LIVE
}

enum class TossApiVersion(
    @get:JsonValue
    val version: String,
) {
    _1_0("1.0"),
    _1_1("1.1"),
    _1_2("1.2"),
    _1_3("1.3"),
    _1_4("1.4"),
    _2022_06_08("2022-06-08"),
    _2022_07_27("2022-07-27"),
    _2022_11_16("2022-11-16"),;

    companion object {
        @JsonCreator
        fun of(value: String): TossApiVersion? =
            TossApiVersion.entries.firstOrNull { it.version == value }
    }
}
