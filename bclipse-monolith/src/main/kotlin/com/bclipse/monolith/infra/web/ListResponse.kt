package com.bclipse.monolith.infra.web

data class ListResponse<T>(
    val data: List<T>,
) {
    val size = data.size
}