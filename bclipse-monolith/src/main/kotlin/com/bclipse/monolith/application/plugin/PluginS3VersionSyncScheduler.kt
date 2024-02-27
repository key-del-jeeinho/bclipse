package com.bclipse.monolith.application.plugin

import com.bclipse.monolith.common.KeyValueStore
import com.bclipse.monolith.infra.Logger.getLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private const val LAST_SYNCED_AT_KEY = "application.plugin.lastSyncedAt"
private const val ZERO = 0L //LAST_SYNCED_AT_KEY_DEFAULT_VALUE
@Component
class PluginS3VersionSyncScheduler(
    private val pluginService: PluginService,
    private val keyValueStore: KeyValueStore,
) {
    private val logger = getLogger()
    @Scheduled(cron = "0 */5 * * * *")
    fun syncVersion() {
        logger.info("'S3 - PersistenceContext간 플러그인 버전 동기화' 작업을 시작합니다.")
        val lastSyncedAtOrNull = keyValueStore.get(LAST_SYNCED_AT_KEY, Long)

        //TODO 기본값을 어떻게 넣어줘야할지 고민해보기
        if(lastSyncedAtOrNull == null) {
            logger.error("lastSyncedAt 설정값을 찾을 수 없습니다!")
            logger.warn("lastSyncedAt을 ${ZERO}으로 설정 후 작업을 진행합니다.")
            keyValueStore.set(LAST_SYNCED_AT_KEY, ZERO)
        }

        val lastSyncedAt = lastSyncedAtOrNull ?: ZERO

        val now = System.currentTimeMillis()
        val result = pluginService.syncVersionFromS3ByUploadRequests(now, lastSyncedAt)
        logger.info("'S3 - PersistenceContext간 플러그인 버전 동기화' 작업이 종료되었습니다.")
        logger.info("작업결과\n{}", result.toString())
        keyValueStore.set(LAST_SYNCED_AT_KEY, now)
    }
}