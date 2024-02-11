package com.bclipse.monolith.infra.mongodb

import org.springframework.data.auditing.DateTimeProvider
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.TemporalAccessor
import java.util.Optional

@Component("dateTimeProvider")
class ZonedDateTimeProvider: DateTimeProvider {
    override fun getNow(): Optional<TemporalAccessor> =
        Optional.of(ZonedDateTime.now(ZoneOffset.UTC))
            .startOfMills()
}

private fun Optional<ZonedDateTime>.startOfMills(): Optional<TemporalAccessor> =
    map { now -> now.withNano((now.nano / 1_000_000) * 1_000_000) }
