package com.bclipse.application.infra

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    fun Any.getLogger(): Logger = LoggerFactory.getLogger(this::class.java)
}