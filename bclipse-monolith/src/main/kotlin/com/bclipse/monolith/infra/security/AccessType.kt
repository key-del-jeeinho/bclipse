package com.bclipse.monolith.infra.security

enum class AccessType(private val value: String) {
    USER("user"), APPLICATION("application"),;

    companion object {
        fun of(value: String): AccessType? {
            return entries.firstOrNull() { it.value == value }
        }
    }
}