package com.bclipse.monolith.application.plugin.entity

import java.math.BigInteger
import java.security.MessageDigest

data class PluginVersion (
    val majorDigit: Int,
    val minorDigit: Int,
    val patchDigit: Int,
    val type: VersionType,
    val fixDigit: Int // "1.2.3-r4" -> 4
): Comparable<PluginVersion> {
    fun getHashId(pluginId: String): String {
        val uniqueKey = toString() + pluginId
        val hash = MessageDigest.getInstance("SHA-256")
            .digest((uniqueKey).toByteArray())
        return BigInteger(1, hash).toString(16).padStart(32, '0')
    }

    override fun toString(): String = "${majorDigit}.${minorDigit}.${patchDigit}-${type.displayString}${fixDigit}"

    override fun compareTo(other: PluginVersion): Int {
        val compareMajor = majorDigit.compareTo(other.majorDigit)
        if(compareMajor != 0 ) return compareMajor

        val compareMinor = minorDigit.compareTo(other.minorDigit)
        if(compareMinor != 0) return compareMinor

        return patchDigit.compareTo(other.patchDigit)
    }

    companion object {
        private val PATTERN = """^(\d+)\.(\d+)\.(\d+)-(\D+)(\d+)$""".toRegex()

        fun from(versionString: String): PluginVersion {
            val lazyError = lazy { IllegalArgumentException("버저닝 정책에 위봔되는 버전입니다. - '$versionString'") } //TODO to WebException

            val (major, miner, patch, typeString, fix) =
                PATTERN.find(versionString)?.destructured
                    ?: throw lazyError.value

            val type = VersionType.fromString(typeString)
                ?: throw lazyError.value

            return PluginVersion(
                majorDigit = major.toInt(),
                minorDigit = miner.toInt(),
                patchDigit = patch.toInt(),
                type = type,
                fixDigit = fix.toInt(),
            )
        }
    }
}