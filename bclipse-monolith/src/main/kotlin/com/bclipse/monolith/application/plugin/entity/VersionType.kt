package com.bclipse.monolith.application.plugin.entity

enum class VersionType(
    val displayString: String
) {
    SNAPSHOT("s"),
    PRE_RELEASE("pre"),
    RELEASE("r"),
    LONG_TERM_SUPPORT("lts");

    companion object {
        fun fromString(typeString: String): VersionType? =
            entries.firstOrNull { type ->
                type.displayString == typeString
            }
    }
}
