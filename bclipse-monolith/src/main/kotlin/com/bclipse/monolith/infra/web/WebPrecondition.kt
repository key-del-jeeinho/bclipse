package com.bclipse.monolith.infra.web

import org.springframework.http.HttpStatus
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

object WebPrecondition {
    @OptIn(ExperimentalContracts::class)
    private fun precondition(value: Boolean, lazyThrowable: Lazy<Throwable>) {
        contract {
            returns() implies value
        }
        if (!value) throw lazyThrowable.value
    }

    @OptIn(ExperimentalContracts::class)
    private fun precondition(value: Boolean, throwableInitializer: () -> Throwable) {
        contract {
            returns() implies value
        }
        precondition(value, lazy(throwableInitializer))
    }

    @OptIn(ExperimentalContracts::class)
    fun preconditionWeb(
        value: Boolean,
        status: HttpStatus,
        lazyThrowable: Lazy<Throwable>
    ) {
        contract {
            returns() implies value
        }
        precondition(value) { WebException(status, lazyThrowable.value) }
    }

    @OptIn(ExperimentalContracts::class)
    fun preconditionWeb(
        value: Boolean,
        status: HttpStatus,
        throwableInitializer: () -> Throwable
    ) {
        contract {
            returns() implies value
        }
        preconditionWeb(value, status, lazy(throwableInitializer))
    }

    @OptIn(ExperimentalContracts::class)
    fun requireRequest(value: Boolean, lazyMessage: Lazy<String>) {
        contract {
            returns() implies value
        }
        preconditionWeb(value, HttpStatus.BAD_REQUEST) {
            IllegalArgumentException(lazyMessage.value)
        }
    }

    @OptIn(ExperimentalContracts::class)
    fun requireRequest(value: Boolean, lazyMessage: () -> String) {
        contract {
            returns() implies value
        }
        requireRequest(value, lazy(lazyMessage))
    }

    @OptIn(ExperimentalContracts::class)
    fun requireState(value: Boolean, lazyMessage: Lazy<String>) {
        contract {
            returns() implies value
        }
        preconditionWeb(value, HttpStatus.CONFLICT) {
            IllegalStateException(lazyMessage.value)
        }
    }

    @OptIn(ExperimentalContracts::class)
    fun requireState(value: Boolean, lazyMessage: () -> String) {
        contract {
            returns() implies value
        }
        requireState(value, lazy(lazyMessage))
    }

    @OptIn(ExperimentalContracts::class)
    fun checkState(value: Boolean, lazyMessage: Lazy<String>) {
        contract {
            returns() implies value
        }
        preconditionWeb(value, HttpStatus.INTERNAL_SERVER_ERROR) {
            IllegalStateException(lazyMessage.value)
        }
    }

    @OptIn(ExperimentalContracts::class)
    fun checkState(value: Boolean, lazyMessage: () -> String) {
        contract {
            returns() implies value
        }
        checkState(value, lazy(lazyMessage))
    }

    @OptIn(ExperimentalContracts::class)
    fun requirePermission(value: Boolean, lazyMessage: Lazy<String>) {
        contract {
            returns() implies value
        }
        preconditionWeb(value, HttpStatus.FORBIDDEN) {
            IllegalAccessException(lazyMessage.value)
        }
    }

    @OptIn(ExperimentalContracts::class)
    fun requirePermission(value: Boolean, lazyMessage: () -> String) {
        contract {
            returns() implies value
        }
        requirePermission(value, lazy(lazyMessage))
    }
}