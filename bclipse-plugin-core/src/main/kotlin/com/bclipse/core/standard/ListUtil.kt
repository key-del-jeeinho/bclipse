package com.bclipse.core.standard

object ListUtil {
    fun List<String>.removeFirst(): List<String> {
        return toMutableList()
            .apply { removeFirstOrNull() }
    }
}