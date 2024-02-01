package com.bclipse.core.standard

fun interface Deferred<T> {
    fun await(): T
}

fun <T> deferred(fn: () -> T): Deferred<T> = Deferred { fn() }
fun <T> deferred(data: T): Deferred<T> = Deferred { data }