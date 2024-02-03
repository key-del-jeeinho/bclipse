package com.bclipse.application.infra.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

object LazyPrecondition {
    @OptIn(ExperimentalContracts::class)
    fun require(value: Boolean, lazyMessage: Lazy<Any>) {
        contract {
            returns() implies value
        }
        require(value) { lazyMessage.value }
    }
}