package com.bclipse.monolith.infra.mongodb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
class MongoConfiguration {
    @Bean
    fun customConversions(): MongoCustomConversions {
        val converters = listOf<Converter<*, *>>(
            DateToZonedDateTimeConverter(),
            ZonedDateTimeToDateConverter(),
        )
        return MongoCustomConversions(converters)
    }

    class DateToZonedDateTimeConverter: Converter<Date, ZonedDateTime> {
        override fun convert(source: Date): ZonedDateTime? =
            ZonedDateTime.ofInstant(
                source.toInstant(),
                ZoneId.systemDefault()
            )
    }

    class ZonedDateTimeToDateConverter: Converter<ZonedDateTime, Date> {
        override fun convert(source: ZonedDateTime): Date? =
            Date.from(source.toInstant())
    }
}