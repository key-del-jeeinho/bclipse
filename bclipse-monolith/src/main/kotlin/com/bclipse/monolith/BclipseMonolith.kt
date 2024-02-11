package com.bclipse.monolith

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableMongoRepositories
class BclipseMonolith

fun main(args: Array<String>) {
    runApplication<BclipseMonolith>(*args)
}
