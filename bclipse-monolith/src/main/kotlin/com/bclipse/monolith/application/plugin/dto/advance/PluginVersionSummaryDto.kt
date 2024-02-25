package com.bclipse.monolith.application.plugin.dto.advance

import com.bclipse.monolith.application.plugin.entity.PluginVersion
import com.bclipse.monolith.application.plugin.entity.VersionType

data class PluginVersionSummaryDto(
    val snapshotVersion: String,
    val releaseVersion: String,
    val ltsVersion: String,
) {
    companion object {
        fun from(
            allVersions: List<PluginVersion>
        ): PluginVersionSummaryDto {
            val 버전이_없는_플러그인의_버전값 = "not found"
            val snapshotVersion = allVersions
                .filter { it.type == VersionType.SNAPSHOT }
                .maxOrNull() ?: 버전이_없는_플러그인의_버전값

            val releaseVersion = allVersions
                .filter { it.type == VersionType.RELEASE }
                .maxOrNull() ?: 버전이_없는_플러그인의_버전값

            val ltsVersion = allVersions
                .filter { it.type == VersionType.LONG_TERM_SUPPORT }
                .maxOrNull() ?: 버전이_없는_플러그인의_버전값

            return PluginVersionSummaryDto(
                snapshotVersion = snapshotVersion.toString(),
                releaseVersion = releaseVersion.toString(),
                ltsVersion = ltsVersion.toString(),
            )
        }
    }
}